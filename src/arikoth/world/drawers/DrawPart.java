package arikoth.world.drawers;

import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawRegion;

public class DrawPart extends DrawRegion {
	public Interp interp = x -> (float)Math.sin(x);

	public float dx = 0, dy = 0, dr = 0, x = 0, y = 0, r = 0;

	public boolean syncToCraft = false, rotateWithBlock = false;

	public DrawPart(String name, float dx, float dy, float dr, Interp interp){
		super(name);
		this.dx = dx;
		this.dy = dy;
		this.dr = dr;
		this.interp = interp;
	}

	public DrawPart(String name, float dx, float dy, float dr){
		this(name, dx, dy, dr, (Interp)(a -> (float)Math.sin(a)));
	}

	public DrawPart(String name, float dx, float dy){
		this(name, dx, dy, 0, (Interp)(a -> (float)Math.sin(a)));
	}

	public DrawPart(String name, float dx, float dy, Interp interp){
		this(name, dx, dy, 0, interp);
	}

	@Override
	public void draw(Building build) {
		float progress;

		if(syncToCraft){
			progress = build.progress();
		} else {
			progress = build.totalProgress();
		}

		float renderX = build.x 										+ x + dx * interp.apply(progress);
		float renderY = build.y 										+ y + dy * interp.apply(progress);
		float renderR = (rotateWithBlock ? 90 * build.rotation : 0) 	+ r + dr * interp.apply(progress);

		Draw.rect(region, renderX, renderY, renderR);
	}

	@Override
	public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
		Draw.rect(region, plan.x*8, plan.y*8, (rotateWithBlock ? 90 * plan.rotation : 0) +r);
	}
}
