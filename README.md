# StealAndEscapeJavaGame
Name likely to change in the future.

Only src and lib files added (out files excluded).

This game is in early development, the game structure's code is still being developed.

Major bugs to fix later:
- Enemy positions glitch; when near a spot they must change direction, they may make a circular motion or change direction too quickly. This can result in them clipping through walls.
- Buttons on level select screen is glitched. This is likely through the drawing process of trying to draw buttons in an evenly organised table.
- Items get picked up even when player does not touch them; hitbox accounts for texture size (64x64) rather than actual item size.
