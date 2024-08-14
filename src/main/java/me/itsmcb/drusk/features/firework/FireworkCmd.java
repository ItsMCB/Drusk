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
import org.bukkit.Color;
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
        super("fw", "Manage and create fireworks", "drusk.firework");
        this.instance = instance;
    }

    @Override
    public void executeAsPlayer(Player player, String[] args) {
        MenuV2 creator = new MenuV2("&8Fireworks", InventoryType.CHEST,9).clickCloseMenu(true)
                .addItem(new SkullBuilder(HeadTexture.GREEN_PLUS.getTexture()).name("&dCreate New").clickAction(e -> {
                    rocketEditor(new ItemStack(Material.FIREWORK_ROCKET),player);
                }))
                .addItem(new SkullBuilder(HeadTexture.TOOL_RACK.getTexture()).name("&dModify Hand").addLore("&7Edit the rocket in your main hand").clickAction(e -> {
                    ItemStack mainHand = player.getInventory().getItemInMainHand().clone();
                    if (!mainHand.getType().equals(Material.FIREWORK_ROCKET)) {
                        new BukkitMsgBuilder("&cYou must hold a rocket in your hand to use this feature!").send(player);
                        return;
                    }
                    rocketEditor(mainHand,player);
                }));
                creator.addItem(new SkullBuilder(HeadTexture.GREEN_PLUS.getTexture()).name("&dPersonal Saves").clickAction(e -> {
                    personalSaves(player,creator);
                }))
                .addItem(new SkullBuilder(HeadTexture.GREEN_PLUS.getTexture()).name("&dPublic Saves").clickAction(e -> {
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

    public MenuV2 showFireworks(Player player, UUID filter) {
        ArrayList<CustomFirework> fireworks = getAllPublicFireworks();
        if (filter != null) {
            fireworks = getAllSavedFireworksFrom(filter);
        }
        MenuV2 saves = new PaginatedMenu("&8"+(((filter == null) ? "Public" : "Your")+" Saves"), 27, player).clickCloseMenu(true);
        fireworks.forEach(fw -> {
            MenuV2Item menuItem = new MenuV2Item(Material.FIREWORK_ROCKET).name("&d" + ((fw.getName() == null) ? "Unnamed Firework" : fw.getName()))
                    .leftClickAction(e -> {
                        player.getInventory().addItem(fw.getItem().clone());
                    });
            if (fw.getCreator().equals(player.getUniqueId())) {
                menuItem.addLore("", "&3Left-click &7to get", "&3Right-click &7to edit");
                menuItem.rightClickAction(e -> {
                    editSave(player, fw);
                });
            }
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
                .addItem(new MenuV2Item(Material.NAME_TAG).name("&dName").clickAction(e -> {
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
        // Public
        CustomFirework modifiedFirework = new CustomFirework(customFirework.getItem(),customFirework.getCreator(),customFirework.isPrivate(),customFirework.getName());
        if (customFirework.isPrivate()) {
            saveEditor.addItem(new SkullBuilder(HeadTexture.BLACKSTONE_BRICKS_LOCKED.getTexture()).name("&dCurrently Private").addLore("&7Click to make public").clickAction(e -> {
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
            saveEditor.addItem(new SkullBuilder(HeadTexture.BLACKSTONE_BRICKS_UNLOCKED.getTexture()).name("&dCurrently Public").addLore("&7Click to make private").clickAction(e -> {
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
        saveEditor.addItem(new MenuV2Item(Material.RED_WOOL).name("&dDelete").clickAction(e -> {
            updateSavedFirework(customFirework,null);
        }));
        instance.getMenuManager().open(saveEditor,player);
    }

    public void rocketEditor(ItemStack firework, Player player) {
        FireworkMeta fwm = (FireworkMeta) firework.getItemMeta();
        MenuV2 creator = new PaginatedMenu("&8Rocket Editor",27,player).clickCloseMenu(true);
        // Obtain
        firework.setAmount(64);
        creator.addItem(new SkullBuilder(HeadTexture.FIREWORK_ROCKET.getTexture()).name("&dObtain Rocket")
                .addLore("&7Get a copy of the current state of the rocket you're editing")
                .clickAction(e -> {
                    player.getInventory().addItem(firework);
                }));
        // Save
        creator.addItem(new SkullBuilder(HeadTexture.GREEN_ARROW_DOWN.getTexture()).name("&dSave").clickAction(e -> {
            if (getAllSavedFireworksFrom(player.getUniqueId()).size() > 24) {
                new BukkitMsgBuilder("&cYou can't create more than 25 saved fireworks at this time.").send(player);
                return;
            }
            ArrayList<CustomFirework> fireworks = getAllSavedFireworks();
            fireworks.add(new CustomFirework(firework,player.getUniqueId(),true,null));
            instance.getFireworks().get().set("fireworks",fireworks);
            instance.getFireworks().save();
            personalSaves(player,null);
        }));
        // Power
        creator.addItem(new MenuV2Item(Material.GUNPOWDER).name("&dPower").addLore("&7Sets the approximate power of the firework.","&7Each level of power is half a second of flight time.").clickAction(e -> {
            power(firework,player,fireworkItemStackConsumer -> {
                rocketEditor(fireworkItemStackConsumer,player); // TODO checkout
            },null);
        }));
        // Effects
        for (int i = 0; i < fwm.getEffects().size(); i++) {
            int finalI = i;
            MenuV2Item effectDisplay = new MenuV2Item(Material.FIREWORK_ROCKET).name("&dEffect #"+(i+1)).clickAction(e -> {
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
            creator.addItem(new SkullBuilder(HeadTexture.GREEN_PLUS.getTexture()).name("&dNew Effect").clickAction(e -> {
                effectEditor(firework, null, player);
            }));
        }
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
        MenuV2 creator = new MenuV2("&8Rocket Editor » Power", InventoryType.CHEST,9).clickCloseMenu(true)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dPower 0").clickAction(e -> {
                    fwm.setPower(0);
                    rocket.setItemMeta(fwm);
                    consumer.accept(rocket);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dPower 1").clickAction(e -> {
                    fwm.setPower(1);
                    rocket.setItemMeta(fwm);
                    consumer.accept(rocket);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dPower 2").clickAction(e -> {
                    fwm.setPower(2);
                    rocket.setItemMeta(fwm);
                    consumer.accept(rocket);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dPower 3").clickAction(e -> {
                    fwm.setPower(3);
                    rocket.setItemMeta(fwm);
                    consumer.accept(rocket);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dPower 4").clickAction(e -> {
                    fwm.setPower(4);
                    rocket.setItemMeta(fwm);
                    consumer.accept(rocket);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dPower 5").clickAction(e -> {
                    fwm.setPower(5);
                    rocket.setItemMeta(fwm);
                    consumer.accept(rocket);
                }))
                .addItem(new MenuV2Item(Material.NAME_TAG).name("&dCustom Input").clickAction(e -> {
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
        creator.addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dType").clickAction(e -> {
            type(builder,player,updateFirework(firework,effectId,player));
        }));
        creator.addItem(new SkullBuilder(HeadTexture.PAINT_BUCKET_RAINBOW.getTexture()).name("&dColor").clickAction(e -> {
            color(builder,player,updateFirework(firework,effectId,player),true);
        }));
        creator.addItem(new SkullBuilder(HeadTexture.PAINT_BUCKET_RAINBOW.getTexture()).name("&dFade Color").clickAction(e -> {
            color(builder,player,updateFirework(firework,effectId,player),false);
        }));
        creator.addItem(new MenuV2Item(Material.GLOWSTONE_DUST).name("&dFlicker").clickAction(e -> {
            flicker(builder,player,updateFirework(firework,effectId,player));
        }));
        creator.addItem(new MenuV2Item(Material.LEVER).name("&dTrail").clickAction(e -> {
            trail(builder,player,updateFirework(firework,effectId,player));
        }));
        creator.addItem(new SkullBuilder(HeadTexture.GREEN_CHECK_MARK.getTexture()).name("&dDone").addLore("&7Go back to the rocket editor").clickAction(e -> {
            firework.setItemMeta(fwm);
            rocketEditor(firework, player);
        }));

        instance.getMenuManager().open(creator,player);
    }

    private void type(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » Type", InventoryType.CHEST,9)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dBall").clickAction(e -> {
                    builder.with(FireworkEffect.Type.BALL);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dLarge Ball").clickAction(e -> {
                    builder.with(FireworkEffect.Type.BALL_LARGE);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dStar").clickAction(e -> {
                    builder.with(FireworkEffect.Type.STAR);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dBurst").clickAction(e -> {
                    builder.with(FireworkEffect.Type.BURST);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dCreeper").clickAction(e -> {
                    builder.with(FireworkEffect.Type.CREEPER);
                    consumer.accept(builder);
                }));
        instance.getMenuManager().open(creator,player);
    }

    private void color(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer, boolean isRegularColor) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » "+(isRegularColor ? "" : "Fade "+"Color"), InventoryType.CHEST,18).clickCloseMenu(true);
        for (DyeColor value : DyeColor.values()) {
            creator.addItem(new MenuV2Item(Material.valueOf(value.name().toUpperCase()+"_DYE")).name("&d"+value.name()).clickAction(e -> {
                if (isRegularColor) {
                    builder.withColor(value.getColor());
                } else {
                    builder.withFade(value.getColor());
                }
                consumer.accept(builder);
            }));
        }
        // Custom input
        creator.addItem(new MenuV2Item(Material.NAME_TAG).name("&dCustom Hex Color Input").clickAction(e -> {
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
        }));
        instance.getMenuManager().open(creator,player);
    }

    public void color(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer, boolean isRegularColor, Color color) {
        if (isRegularColor) {
            builder.withColor(color);
        } else {
            builder.withFade(color);
        }
        consumer.accept(builder);
    }

    private void flicker(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » Flicker", InventoryType.CHEST,9)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dTrue").clickAction(e -> {
                    builder.flicker(true);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dFalse").clickAction(e -> {
                    builder.flicker(false);
                    consumer.accept(builder);
                }));
        instance.getMenuManager().open(creator,player);
    }

    private void trail(FireworkEffect.Builder builder, Player player, Consumer<FireworkEffect.Builder> consumer) {
        MenuV2 creator = new MenuV2("&8Rocket Editor » Trail", InventoryType.CHEST,9)
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dTrue").clickAction(e -> {
                    builder.trail(true);
                    consumer.accept(builder);
                }))
                .addItem(new MenuV2Item(Material.FIREWORK_STAR).name("&dFalse").clickAction(e -> {
                    builder.trail(false);
                    consumer.accept(builder);
                }));
        instance.getMenuManager().open(creator,player);
    }
}
