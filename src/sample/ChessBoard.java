package sample;

import javafx.scene.layout.GridPane;

public class ChessBoard extends GridPane {
    public Space[][] spaces = new Space[12][12];
    // const

    //last clicked space
    public Space activeSpace = null;

    public ChessBoard() {
        //cause always call super
        super();
        // initialize 8x8 array of spaces
        for (int x = 0; x < spaces[0].length; x++) {
            for (int y = 0; y < spaces[1].length; y++) {
                spaces[x][y] = new Space(x, y);
                this.add(spaces[x][y], x, y);
                // Gets values into event handler
                final int xVal = x;
                final int yVal = y;
                //runs things that happen when a space is clicked
                spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));
            }
        }

        //put pieces in start positions
        this.defineStartPositions();
    }

    // Use this to get a space, using GridPane methods will (I think) cause color problems
    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }

    public void setActiveSpace(Space s) {
        // Remove style from old active space
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().removeAll("space-active");

        this.activeSpace = s;

        // Add style to new active space
        if (this.activeSpace != null)
            this.activeSpace.getStyleClass().add("space-active");
    }

    // Use this to get a space, using GridPane methods will (I think) cause color problems
    public Space getActiveSpace() {
        return this.activeSpace;
    }

    //define the starting piece positions
    public void defineStartPositions() {
        for (int i = 0; i < spaces[0].length; i++) {
            for (int j = 0; j < spaces[1].length; j++) {
                this.spaces[i][j].setPiece(new Knight(true));
            }
        }
    }

    public void onSpaceClick(int x, int y) {
        // if piece is selected
        if (activeSpace != null && activeSpace.getPiece() != null) {
            MoveInfo p;
            p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);

            // update gameboard
            if (this.processMove(p)) {
//                this.setDisable(true);
            }
            //decouples space from space on board
            this.setActiveSpace(null);
        } else {
            //if there's a piece on the selected square when no active square
            if (spaces[x][y].getPiece() != null) {
                //make active square clicked square
                this.setActiveSpace(spaces[x][y]);
            }
        }
    }

    // Process a move after it has been made by a player
    protected boolean processMove(MoveInfo p) {
        if (moveIsValid(p)) {
            Space oldSpace = spaces[p.getOldX()][p.getOldY()];
            Space newSpace = spaces[p.getNewX()][p.getNewY()];

            newSpace.setPiece(oldSpace.releasePiece());
            return true;
        } else // invalid move
        {
            return false;
        }
    }

    public boolean moveIsValid(MoveInfo p) {
        Space oldSpace;
        Space newSpace;
        Piece piece;
        MoveList[] moves;
        // Check for null move
        if (p == null) {
            return false;
        }
        // Check if oldSpace in range
        try {
            oldSpace = spaces[p.getOldX()][p.getOldY()];
        } catch (NullPointerException e) {
            return false;
        }
        // Check if newSpace in range
        try {
            newSpace = spaces[p.getNewX()][p.getNewY()];
        } catch (NullPointerException e) {
            return false;
        }
        // Check if oldSpace is empty; (no movable piece)
        if (!oldSpace.isOccupied()) {
            return false;
        }
        // Check piece's move list
        piece = oldSpace.getPiece();
        moves = piece.getPieceMoves();
        boolean matchesPieceMoves = false;
        for (MoveList m : moves) {
            if (m.getX() == p.getGapX() && m.getY() == -p.getGapY()) matchesPieceMoves = true;
        }
//        //for Pieces that move more than 1 base move (Bishop, Rook, Queen)
//        int multiMoveCount;
//        int stretchedMoveX;
//        int stretchedMoveY;
//
//        //labels this loop to break out later
//        MoveLoop:
//        for (MoveList m : moves)
//        {//iterates through multiple times if has multiple possible moves
//            multiMoveCount = 1;
//            if(piece.usesSingleMove() == false) {multiMoveCount = 8;}
//
//            boolean hasCollided = false;
//
//            for(int c = 1; c <= multiMoveCount; c++)
//            {
//                //if the prior run hit a piece of opponent's color, done with this move
//                if (hasCollided){break;}
//
//                //stretches a base move out to see if it matches the move made
//                stretchedMoveX = m.getX() * c;
//                stretchedMoveY = m.getY() * c;
//
//                Space tempSpace;
//
//                //If OOB, go to next move of the piece -- ensures space exists later
//                try
//                {
//                    tempSpace = spaces[p.getOldX() + stretchedMoveX]
//                    [p.getOldY() + stretchedMoveY];
//                }
//                catch (Exception e) { break; }
//
//                //handles piece collision and capturing
//                if(tempSpace.isOccupied())
//                {
//                    hasCollided = true;
//                    boolean piecesSameColor = tempSpace.getPiece().getColor() == oldSpace.getPiece().getColor();
//                    //stops checking this move if pieces are the same color
//                    if (piecesSameColor){ break; }
//                }
//
//                //if stretched move matches made move
//                if ( p.getGapX() == stretchedMoveX && p.getGapY() == stretchedMoveY)
//                {
//                    matchesPieceMoves = true;
//
//                    piece.setHasMoved(true);
//                    //breaks out of MoveLoop (both loops)
//                    break MoveLoop;
//                }
//            }
//        }
        return matchesPieceMoves;
    }
}
