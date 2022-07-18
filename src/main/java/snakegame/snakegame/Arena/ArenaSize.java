package snakegame.snakegame.Arena;

public enum ArenaSize {
    SMALL(11,11), MEDIUM(27,27), LARGE(51,51);
    private int w;
    private int h;
    ArenaSize(int w, int h){
        this.w=w;
        this.h=h;
    }


    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
