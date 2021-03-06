package sample;

public class Bishop extends Piece {
    public Bishop(boolean color) {
        //this calls the constructor of Piece
        super(color);
    }

    protected MoveList[] getPieceMoves() {
        MoveList[] m =
                {
                        MoveList.KNIGHT_UP_LEFT,
                        MoveList.KNIGHT_UP_RIGHT,
                        MoveList.UP,
                        MoveList.DOUBLE_UP,
                        MoveList.UP_LEFT,
                        MoveList.UP_RIGHT,
                };
        return m;
    }

    protected boolean usesSingleMove() {
        return false;
    }

    protected String getName() {
        return "bishop";
    }
}
