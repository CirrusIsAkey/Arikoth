package arikoth.math;

import arc.math.Interp;
import arc.math.Mathf;
public class ArikothInterps{
    public static final Interp parabola = x -> (1 - Mathf.sqr((2 * x) - 1));
    public static final Interp speedInterp = x ->(0.008f * Mathf.sqr(x));
}
