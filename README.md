# Chess
This repository contains the Java code for a complete running version of the **chess game** for the Software Development Methods course, a.y. 2024/2025.

## Chess Overview
Chess is a strategic board game played between two players. It is played on an 8x8 grid called a *Chessboard*, which alternates between light and dark squares.

## Chessboard Layout
- **Size**: 8x8 grid (64 squares)
- **Colors**: Alternating light and dark squares
- **Depiction**: Columns, called *Files*, labeled A-H and Rows, called *Ranks*, labeled 1-8

## Players and Pieces
- **Colors**: White and Black
- **Total Pieces per Player**: 16
    - **Pawns**: 8
    - **Rooks**: 2
    - **Knights**: 2
    - **Bishops**: 2
    - **Queen**: 1
    - **King**: 1

## Initial Setup
| Piece   | Initial Position (White) | Initial Position (Black) |
|---------|---------------------------|---------------------------|
| Pawns   | Second row (A2-H2)        | Seventh row (A7-H7)       |
| Rooks   | A1, H1                    | A8, H8                    |
| Knights | B1, G1                    | B8, G8                    |
| Bishops | C1, F1                    | C8, F8                    |
| Queen   | D1                        | D8                        |
| King    | E1                        | E8                        |

## Piece Values
- **Pawn**: 1 point
- **Knight**: 3 points
- **Bishop**: 3 points
- **Rook**: 5 points
- **Queen**: 9 points
- **King**: Infinite (game ends if captured)

## Movement and Capturing
- **General Rules**:
    - Moves must stay within the 8x8 chessboard.
    - A piece cannot move to a square occupied by another piece of the same color.
    - A piece cannot move through another piece, except for the knight.
    - A piece can capture the first opposing piece in its path.

### Pawn
- **Movement**: 
    - 1 square forward
    - 2 squares forward on its first move
- **Capturing**: Diagonally 1 square forward

### Rook
- **Movement**: Horizontally or vertically any number of squares
- **Capturing**: Same as movement

### Knight
- **Movement**: In an "L" shape (2 squares in one direction and then 1 square perpendicular)
- **Capturing**: Same as movement

### Bishop
- **Movement**: Diagonally any number of squares
- **Capturing**: Same as movement

### Queen
- **Movement**: Horizontally, vertically, or diagonally any number of squares
- **Capturing**: Same as movement

### King
- **Movement**: 1 square in any direction
- **Capturing**: Same as movement