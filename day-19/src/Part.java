import java.util.Arrays;

public class Part {
    int x;
    int m;
    int a;
    int s;
    String currentWorkFlowName;
    boolean isAccepted;
    boolean isRejected;
    int[] xRange;
    int[] mRange;
    int[] aRange;
    int[] sRange;

    public Part(int x, int m, int a, int s) {
        xRange = new int[]{0,4000};
        mRange = new int[]{0,4000};
        aRange = new int[]{0,4000};
        sRange = new int[]{0,4000};
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }
}
