package me.itsmcb.drusk.features.firework;

import me.itsmcb.drusk.Drusk;
import me.itsmcb.vexelcore.bukkit.api.command.CustomCommand;
import me.itsmcb.vexelcore.bukkit.api.conversation.InputPrefix;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2;
import me.itsmcb.vexelcore.bukkit.api.menuv2.MenuV2Item;
import me.itsmcb.vexelcore.bukkit.api.menuv2.PaginatedMenu;
import me.itsmcb.vexelcore.bukkit.api.menuv2.SkullBuilder;
import me.itsmcb.vexelcore.bukkit.api.text.BukkitMsgBuilder;
import me.itsmcb.vexelcore.common.api.HeadTexture;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

public class FireworkCmd extends CustomCommand {

    private Drusk instance;

    public FireworkCmd(Drusk instance) {
        super("fw", "Create, modify, save, and share fireworks", "drusk.firework");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        MenuV2 creator = new MenuV2("&8Libre Galaxy Fireworks", InventoryType.CHEST,9).clickCloseMenu(true)
                .addItem(new SkullBuilder(HeadTexture.GREEN_PLUS.getTexture()).name("&3Create New").slot(1).addLore("&7Create a firework from scratch","","&3➤ &7Click to open editor").clickAction(e -> {
                    rocketEditor(new ItemStack(Material.FIREWORK_ROCKET),player);
                }))
                .addItem(new SkullBuilder(HeadTexture.TOOL_RACK.getTexture()).name("&3Modify Hand").slot(3).addLore("&7Edit the rocket in your main hand","","&3➤ &7Click to open editor").clickAction(e -> {
                    ItemStack mainHand = player.getInventory().getItemInMainHand().clone();
                    if (!mainHand.getType().equals(Material.FIREWORK_ROCKET)) {
                        new BukkitMsgBuilder("&cYou must hold a rocket in your hand to use this feature!").send(player);
                        return;
                    }
                    rocketEditor(mainHand,player);
                }));
                creator.addItem(new SkullBuilder(HeadTexture.GREEN_HEART.getTexture()).name("&3Personal Saves").slot(5).addLore("&7Fireworks you've created and saved","","&3➤ &7Click to view").clickAction(e -> {
                    personalSaves(player,creator);
                }))
                .addItem(new SkullBuilder(HeadTexture.CLASSIC_GLOBE.getTexture()).name("&3Public Saves").slot(7).addLore("&7Fireworks made by others","","&3➤ &7Click to view").clickAction(e -> {
                    publicSaves(player,creator);
                }));
        instance.getMenuManager().open(creator,player);
    }

    public ArrayList<CustomFirework> getAllSavedFireworks() {
        ArrayList<CustomFirework> fireworks = (ArrayList<CustomFirework>) instance.getFireworks().get().get("fireworks");
        if (fireworks == null) {
            fireworks = new ArrayList<>();
        }
        return fireworks;
    }

    public ArrayList<CustomFirework> getAllSavedFireworksFrom(UUID uuid) {
        return new ArrayList<>(getAllSavedFireworks().stream().filter(fw -> fw.getCreator().equals(uuid)).toList());
    }

    public ArrayList<CustomFirework> getAllPublicFireworks() {
        return new ArrayList<>(getAllSavedFireworks().stream().filter(fw -> !fw.isPrivate()).toList());
    }

    public void updateSavedFirework(CustomFirework oldData, CustomFirework newData) {
        ArrayList<CustomFirework> fireworks = getAllSavedFireworks();
        fireworks.remove(oldData);
        if (newData != null) {
            fireworks.add(newData);
        }
        instance.getFireworks().get().set("fireworks",fireworks);
        instance.getFireworks().save();
    }

    private String fireworkName(CustomFirework cf) {
        return "&3" + ((cf.getName() == null) ? "Unnamed Firework" : cf.getName());
    }

    public MenuV2 showFireworks(Player player, UUID filter) {
        ArrayList<CustomFirework> fireworks = getAllPublicFireworks();
        if (filter != null) {
            fireworks = getAllSavedFireworksFrom(filter);
        }
        MenuV2 saves = new PaginatedMenu("&8"+(((filter == null) ? "Public" : "Your")+" Saves"), 27, player).clickCloseMenu(true);
        fireworks.forEach(fw -> {
            MenuV2Item menuItem = new MenuV2Item(Material.FIREWORK_ROCKET).name(fireworkName(fw)).clickAction(e -> editSave(player,fw));
            saves.addItem(menuItem);
        });
        return saves;
    }
    public void personalSaves(Player player, MenuV2 previous) {
        instance.getMenuManager().open(showFireworks(player,player.getUniqueId()),player,previous);
    }

    public void publicSaves(Player player, MenuV2 previous) {
        instance.getMenuManager().open(showFireworks(player,null),player,previous);
    }

    public void editSave(Player player, CustomFirework customFirework) {
        MenuV2 saveEditor = new MenuV2("&8Edit Saved Firework",InventoryType.CHEST,9).clickCloseMenu(true)
                .addItem(new MenuV2Item(Material.FIREWORK_ROCKET).slot(1).name(fireworkName(customFirework))
                        .addLore("&7Add a copy to your inventory","","&3➤ &7Click to obtain")
                        .clickAction(e -> {
                            player.getInventory().addItem(customFirework.getItem().clone());
                        }));
        if (customFirework.getCreator().equals(player.getUniqueId())) {
            // Name
            saveEditor.addItem(new MenuV2Item(Material.NAME_TAG).slot(4).name("&3Name").clickAction(e -> {
                Conversation c = new ConversationFactory(instance)
                        .withFirstPrompt(new FireworkNamePrompt())
                        .withEscapeSequence("exit")
                        .withTimeout(60)
                        .withPrefix(new InputPrefix())
                        .withLocalEcho(false)
                        .buildConversation(player);
                c.getContext().setSessionData("drusk",instance);
                c.getContext().setSessionData("customFirework",customFirework);
                c.begin();
            }));
            // Sharing
            CustomFirework modifiedFirework = new CustomFirework(customFirework.getItem(),customFirework.getCreator(),customFirework.isPrivate(),customFirework.getName());
            if (customFirework.isPrivate()) {
                saveEditor.addItem(new SkullBuilder(HeadTexture.BLACKSTONE_BRICKS_LOCKED.getTexture()).slot(5).name("&3Currently Private").addLore("&7Click to make public").clickAction(e -> {
                    modifiedFirework.setIsPrivate(false);
                    updateSavedFirework(customFirework,modifiedFirework);
                    // Delay for save function
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            publicSaves(player,null);
                        }
                    }.runTaskLater(instance,5L);
                }));
            } else {
                saveEditor.addItem(new SkullBuilder(HeadTexture.BLACKSTONE_BRICKS_UNLOCKED.getTexture()).slot(5).name("&3Currently Public").addLore("&7Click to make private").clickAction(e -> {
                    modifiedFirework.setIsPrivate(true);
                    updateSavedFirework(customFirework,modifiedFirework);
                    // Delay for save function
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            personalSaves(player,null);
                        }
                    }.runTaskLater(instance,5L);
                }));
            }
            // Delete
            saveEditor.addItem(new SkullBuilder(HeadTexture.RED_X.getTexture()).slot(6).name("&3Delete").clickAction(e -> {
                updateSavedFirework(customFirework,null);
            }));
        }
        // Exit
        saveEditor.addItem(new SkullBuilder(HeadTexture.GRAY_ARROW_LEFT.getTexture()).slot(7).name("&3Exit").addLore("&3➤ &7Go back to the home menu").clickAction(e -> {
            new FireworkCmd(instance).executeAsPlayer(player);
        }));
        instance.getMenuManager().open(saveEditor,player);
    }

    public void rocketEditor(ItemStack firework, Player player) {
        FireworkMeta fwm = (FireworkMeta) firework.getItemMeta();
        MenuV2 creator = new PaginatedMenu("&8Rocket Editor",27,player).clickCloseMenu(true);
        // Obtain
        firework.setAmount(64);
        creator.addItem(new SkullBuilder(HeadTexture.FIREWORK_ROCKET.getTexture()).name("&3Create")
                .addLore("&7Add a copy of current firework state to your inventory","","&3➤ &7Click to obtain")
                .clickAction(e -> {
                    player.getInventory().addItem(firework);
                }));
        // Save
        creator.addItem(new SkullBuilder(HeadTexture.GREEN_ARROW_DOWN.getTexture()).name("&3Save")
                .addLore("&7Saves current firework state for later access and sharing","","&3➤ &7Click to save")
                .clickAction(e -> {
                    if (getAllSavedFireworksFrom(player.getUniqueId()).size() > 29) {
                        new BukkitMsgBuilder("&cYou can't create more than 30 saved fireworks at this time.").send(player);
                        return;
                    }
                    ArrayList<CustomFirework> fireworks = getAllSavedFireworks();
                    fireworks.add(new CustomFirework(firework,player.getUniqueId(),true,null));
                    instance.getFireworks().get().set("fireworks",fireworks);
                    instance.getFireworks().save();
                    personalSaves(player,null);
                }));
        // Power
        creator.addItem(new MenuV2Item(Material.GUNPOWDER).name("&3Power")
                .addLore(
                        "&7Sets the approximate power of the firework.",
                        "&7Each level of power is half a second of flight time.","",
                        "&3ℹ &7Currently: &3"+fwm.getPower(),"",
                        "&3➤ &7Click to select power level"
                )
                .clickAction(e -> {
                    power(firework,player,fireworkItemStackConsumer -> {
                        rocketEditor(fireworkItemStackConsumer,player);
                    },null);
                }));
        // Effects
        for (int i = 0; i < fwm.getEffects().size(); i++) {
            int finalI = i;
            MenuV2Item effectDisplay = new MenuV2Item(Material.FIREWORK_ROCKET).name("&3Effect #"+(i+1)).clickAction(e -> {
                effectEditor(firework, finalI, player);
            });
            FireworkEffect effect = fwm.getEffects().get(0);
            effectDisplay.addLore("&8╠═ &7Type: &3"+effect.getType().name());
            effectDisplay.addLore("&8╠═ &7Flicker: &3"+effect.hasFlicker());
            effectDisplay.addLore("&8╠═ &7Trail: &3"+effect.hasTrail());
            effectDisplay.addLore("&8╠═ &7Colors");
            effect.getColors().forEach(color -> {
                String hex = TextColor.color(color.getRed(),color.getGreen(),color.getBlue()).asHexString();
                effectDisplay.addLore("&8╠══ &3"+hex);
            });
            effectDisplay.addLore("&8╠═ &7Fade Colors");
            effect.getFadeColors().forEach(color -> {
                String hex = TextColor.color(color.getRed(),color.getGreen(),color.getBlue()).asHexString();
                effectDisplay.addLore("&8╠══ &3"+hex);
            });
            creator.addItem(effectDisplay);
        }
        // New effect
        if (!(fwm.getEffects().size() > 10)) {
            creator.addItem(new SkullBuilder(HeadTexture.GREEN_PLUS.getTexture()).name("&3New Effect")
                    .addLore("&7Add another effect","","&3➤ &7Click to open new effect editor")
                    .clickAction(e -> {
                        effectEditor(firework, null, player);
                    }));
        }
        creator.addItem(new SkullBuilder(HeadTexture.RED_ARROW_LEFT.getTexture()).name("&3Quit Without Saving").addLore("&3➤ &7Go back to the home menu").clickAction(e -> {
            new FireworkCmd(instance).executeAsPlayer(player);
        }));
        instance.getMenuManager().open(creator,player);
    }

    private Consumer<FireworkEffect.Builder> updateFirework(ItemStack firework, Integer effectId, Player player) {
        return c -> {
            FireworkMeta fwm = (FireworkMeta) firework.getItemMeta();
            // Remove old
            if (effectId != null) {
                fwm.removeEffect(effectId);
            }
            // Update
            fwm.addEffect(c.build());
            firework.setItemMeta(fwm);
            // Re-open menu
            if (effectId == null) {
                effectEditor(firework, fwm.getEffectsSize()-1, player);
            } else {
                effectEditor(firework,effectId,player);
            }
        };
    }

    public void power(ItemStack rocket, Player player, Consumer<ItemStack> consumer, Integer level) {
        FireworkMeta fwm = (FireworkMeta) rocket.getItemMeta();
        if (level != null) {
            fwm.setPower(level);
            rocket.setItemMeta(fwm);
            consumer.accept(rocket);
            return;
        }
        MenuV2 creator = new MenuV2("&8Rocket Editor » Power", InventoryType.CHEST,9).clickCloseMenu(true);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            creator.addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Power "+i)
                    .addLore("&7Set the rocket's power level to "+i,"","&3➤ &7Click to set power level")
                    .clickAction(e -> {
                        fwm.setPower(finalI);
                        rocket.setItemMeta(fwm);
                        consumer.accept(rocket);
                    }));
        }
        creator.addItem(new MenuV2Item(Material.NAME_TAG).name("&3Custom Input").addLore("&7Set power level by typing an integer from 0-10 in chat","","&3➤ &7Click to type value in chat").clickAction(e -> {
            Conversation c = new ConversationFactory(instance)
                    .withFirstPrompt(new FireworkPowerPrompt())
                    .withEscapeSequence("exit")
                    .withTimeout(60)
                    .withPrefix(new InputPrefix())
                    .withLocalEcho(false)
                    .buildConversation(player);
            c.getContext().setSessionData("drusk",instance);
            c.getContext().setSessionData("rocket",rocket);
            c.begin();
        }));
        creator.addItem(new SkullBuilder(HeadTexture.RED_ARROW_LEFT.getTexture()).name("&3Back").addLore("&3➤ &7Go back to the previous menu").clickAction(e -> {
            consumer.accept(rocket);
        }));
        instance.getMenuManager().open(creator,player);
    }

    public void effectEditor(ItemStack firework, Integer effectId, Player player) {
        // Select effect
        FireworkMeta fwm = (FireworkMeta) firework.getItemMeta();
        FireworkEffect effect = FireworkEffect.builder().build();
        if (effectId != null) {
            effect = fwm.getEffects().get(effectId);
        }
        // Apply previous effects to builder to support modification
        FireworkEffect.Builder builder = FireworkEffect.builder().with(effect.getType()).flicker(effect.hasFlicker()).trail(effect.hasTrail()).withColor(effect.getColors()).withFade(effect.getFadeColors());
        // Editor
        MenuV2 creator = new MenuV2("&8Rocket Editor » Effect", InventoryType.CHEST,9);
        creator.addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Type")
                .addLore(
                        "&7The shape of the explosion","",
                        "&3ℹ &7Currently: &3"+effect.getType().name(),"",
                        "&3➤ &7Click to configure"
                )
                .clickAction(e -> {
                    type(builder,player,updateFirework(firework,effectId,player));
                }));
        // Primary color
        MenuV2Item color = new SkullBuilder(HeadTexture.PAINT_BUCKET_RAINBOW.getTexture()).name("&3Initial Color").addLore("&7The colors of the initial particles of","&7the explosion, randomly selected from","").clickAction(e -> {
            color(builder,player,updateFirework(firework,effectId,player),true);
        });
        if (!effect.getColors().isEmpty()) {
            color.addLore("&3ℹ &7Currently");
            effect.getColors().forEach(ec -> {
                String hex = TextColor.color(ec.getRed(),ec.getGreen(),ec.getBlue()).asHexString();
                color.addLore("&8╠══ &3"+hex);
            });
            color.addLore("&7");
        }
        color.addLore("&3➤ &7Click to configure");
        creator.addItem(color);
        // Fade color
        MenuV2Item fadeColor = new SkullBuilder(HeadTexture.PAINT_BUCKET_RAINBOW.getTexture()).name("&3Fade Color").addLore("&7The colors of the fading particles of","&7the explosion, randomly selected from","").clickAction(e -> {
            color(builder,player,updateFirework(firework,effectId,player),false);
        });
        if (!effect.getColors().isEmpty()) {
            fadeColor.addLore("&3ℹ &7Currently");
            effect.getColors().forEach(ec -> {
                String hex = TextColor.color(ec.getRed(),ec.getGreen(),ec.getBlue()).asHexString();
                fadeColor.addLore("&8╠══ &3"+hex);
            });
            fadeColor.addLore("&7");
        }
        fadeColor.addLore("&3➤ &7Click to configure");
        creator.addItem(fadeColor);
        // Flicker
        creator.addItem(new MenuV2Item(Material.GLOWSTONE_DUST).name("&3Flicker")
                .addLore(
                        "&7Whether or not the explosion has a twinkle effect","",
                        "&3ℹ &7Currently: &3"+effect.hasFlicker(),"",
                        "&3➤ &7Click to configure"
                )
                .clickAction(e -> {
                    flicker(builder,player,updateFirework(firework,effectId,player));
                }));
        creator.addItem(new MenuV2Item(Material.LEVER).name("&3Trail")
                .addLore(
                        "&7Whether or not the explosion has a trail effect","",
                        "&3ℹ &7Currently: &3"+effect.hasTrail(),"",
                        "&3➤ &7Click to configure"
                )
                .clickAction(e -> {
                    trail(builder,player,updateFirework(firework,effectId,player));
                }));
        creator.addItem(new SkullBuilder(HeadTexture.RED_X.getTexture()).name("&3Remove").addLore("&7Remove this effect").clickAction(e -> {
            if (effectId != null) {
                fwm.removeEffect(effectId);
            }
            firework.setItemMeta(fwm);
            rocketEditor(firework, player);
        }));
        creator.addItem(new SkullBuilder(HeadTexture.GREEN_CHECK_MARK.getTexture()).name("&3Done").addLore("&7Go back to the rocket editor").clickAction(e -> {
            firework.setItemMeta(fwm);
            rocketEditor(firework, player);
        }));

        instance.getMenuManager().open(creator,player);
    }

    private void type(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » Type", InventoryType.CHEST,9)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Ball").clickAction(e -> {
                    builder.with(FireworkEffect.Type.BALL);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Large Ball").clickAction(e -> {
                    builder.with(FireworkEffect.Type.BALL_LARGE);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Star").clickAction(e -> {
                    builder.with(FireworkEffect.Type.STAR);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Burst").clickAction(e -> {
                    builder.with(FireworkEffect.Type.BURST);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3Creeper").clickAction(e -> {
                    builder.with(FireworkEffect.Type.CREEPER);
                    consumer.accept(builder);
                }))
                .addItem(new SkullBuilder(HeadTexture.RED_ARROW_LEFT.getTexture()).name("&3Back").addLore("&3➤ &7Go back to the previous menu").clickAction(e -> {
                    consumer.accept(builder);
                }));
        instance.getMenuManager().open(creator,player);
    }

    private void color(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer, boolean isRegularColor) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » "+(isRegularColor ? "" : "Fade "+"Color"), InventoryType.CHEST,18).clickCloseMenu(true);
        for (DyeColor value : DyeColor.values()) {
            creator.addItem(new MenuV2Item(Material.valueOf(value.name().toUpperCase()+"_DYE")).name("&3"+value.name()).clickAction(e -> {
                if (isRegularColor) {
                    builder.withColor(value.getColor());
                } else {
                    builder.withFade(value.getColor());
                }
                consumer.accept(builder);
            }));
        }
        // Custom input
        creator.addItem(new MenuV2Item(Material.NAME_TAG).name("&3Custom Hex Color Input").clickAction(e -> {
            Conversation c = new ConversationFactory(instance)
                    .withFirstPrompt(new FireworkColorPrompt())
                    .withEscapeSequence("exit")
                    .withTimeout(60)
                    .withPrefix(new InputPrefix())
                    .withLocalEcho(false)
                    .buildConversation(player);
            c.getContext().setSessionData("drusk",instance);
            c.getContext().setSessionData("isRegularColor",isRegularColor);
            c.getContext().setSessionData("builder",builder);
            c.getContext().setSessionData("consumer",consumer);
            c.begin();
        }))
        .addItem(new SkullBuilder(HeadTexture.RED_ARROW_LEFT.getTexture()).name("&3Back").addLore("&3➤ &7Go back to the previous menu").clickAction(e -> {
            consumer.accept(builder);
        }));
        instance.getMenuManager().open(creator,player);
    }

    private void flicker(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » Flicker", InventoryType.CHEST,9)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3True").clickAction(e -> {
                    builder.flicker(true);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3False").clickAction(e -> {
                    builder.flicker(false);
                    consumer.accept(builder);
                }))
                .addItem(new SkullBuilder(HeadTexture.RED_ARROW_LEFT.getTexture()).name("&3Back").addLore("&3➤ &7Go back to the previous menu").clickAction(e -> {
                    consumer.accept(builder);
                }));
        instance.getMenuManager().open(creator,player);
    }

    private void trail(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » Trail", InventoryType.CHEST,9)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3True").clickAction(e -> {
                    builder.trail(true);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&3False").clickAction(e -> {
                    builder.trail(false);
                    consumer.accept(builder);
                }))
                .addItem(new SkullBuilder(HeadTexture.RED_ARROW_LEFT.getTexture()).name("&3Back").addLore("&3➤ &7Go back to the previous menu").clickAction(e -> {
                    consumer.accept(builder);
                }));
        instance.getMenuManager().open(creator,player);
    }
}
