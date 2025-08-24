# Chess

This repository contains the Java implementation of a **complete running version of the chess game** developed for the *Software Development Methods* course (a.y. 2024/2025).  

The project provides both the **core game logic** (rules, movement, validation, win conditions) and a **graphical user interface (GUI)** that allows two players to play against each other in a timed setting.

---

## 📖 Game Overview
Chess is a two-player strategic board game played on an 8×8 grid, the *chessboard*. Each player commands 16 pieces of either **white** or **black**, and the objective is to checkmate the opponent's king.  

---

## ♟️ Chessboard Layout
- **Size**: 8×8 grid (64 squares total)  
- **Colors**: Alternating light and dark squares  
- **Notation**:  
  - Columns are called *Files*, labeled **A–H** (left to right from White’s perspective).  
  - Rows are called *Ranks*, labeled **1–8** (bottom to top from White’s perspective).  

---

## 👥 Players and Pieces
Each player controls **16 pieces** at the start of the game:  

- **Pawns (×8)**  
- **Rooks (×2)**  
- **Knights (×2)**  
- **Bishops (×2)**  
- **Queen (×1)**  
- **King (×1)**  

---

## 🔹 Initial Setup
| Piece   | Initial Position (White) | Initial Position (Black) |
|---------|---------------------------|---------------------------|
| Pawns   | A2–H2                     | A7–H7                     |
| Rooks   | A1, H1                    | A8, H8                    |
| Knights | B1, G1                    | B8, G8                    |
| Bishops | C1, F1                    | C8, F8                    |
| Queen   | D1                        | D8                        |
| King    | E1                        | E8                        |

---

## 📊 Piece Values
While the king cannot be assigned a numeric value (since its capture ends the game), conventional chess assigns approximate point values to pieces for evaluation:

- **Pawn**: 1  
- **Knight**: 3  
- **Bishop**: 3  
- **Rook**: 5  
- **Queen**: 9  
- **King**: ∞ (priceless)  

---

## 🔄 Movement and Capturing Rules
### General Rules
- Moves must remain within the 8×8 chessboard.  
- A piece **cannot move to a square occupied by another piece of the same color**.  
- With the exception of the knight, pieces **cannot move through other pieces**.  
- Capturing occurs by replacing an opponent’s piece on its square.  

### Piece Movements
- **Pawn**:  
  - Moves forward 1 square, or 2 squares forward from its starting position.  
  - Captures **diagonally forward by 1 square**.  
- **Rook**:  
  - Moves any number of squares **horizontally or vertically**.  
- **Knight**:  
  - Moves in an “L” shape: **2 squares in one direction, then 1 square perpendicular**.  
  - Can jump over other pieces.  
- **Bishop**:  
  - Moves any number of squares **diagonally**.  
- **Queen**:  
  - Moves any number of squares in **any direction** (horizontal, vertical, diagonal).  
- **King**:  
  - Moves **1 square** in any direction.  

---

## ⭐ Special Moves
In addition to standard movement, chess includes **special moves** with specific conditions. This implementation currently supports:  

### 1. Promotion
- **When**: If a pawn reaches the last rank (rank 8 for White, rank 1 for Black).  
- **How**: It must be promoted to another piece.  
- **Implementation**: Currently, promotion is **always to a Queen** (automatic queen promotion).  
- **Example**: If a White pawn moves from E7 → E8, it becomes a White Queen.  

---

### 2. Castling
- **When**: A special king + rook move performed under these conditions:  
  - Neither the king nor the rook involved has moved previously.  
  - The squares between the king and rook are unoccupied.  
  - The king is not currently in check.  
  - The king cannot move through or end up in a square that is under attack.  

- **How**:  
  - The king moves **two squares towards the rook**.  
  - The rook moves to the square immediately on the opposite side of the king.  

- **Types**:  
  - **Kingside castling** (short castling): King from E1 → G1 and rook from H1 → F1 (for White).  
  - **Queenside castling** (long castling): King from E1 → C1 and rook from A1 → D1 (for White).  
  - The same applies symmetrically for Black.  

- **Example**: White king on E1 and rook on H1, with squares F1 and G1 empty and not attacked → White can castle kingside.  

---

### 3. En Passant
- **When**: If a pawn advances two squares forward from its starting position and passes through a square that could have been attacked by an opposing pawn.  
- **How**: The opposing pawn may capture it **as if it had only moved one square forward**.  
- **Condition**: This move must be made **immediately on the following turn**, otherwise the opportunity is lost.  

- **Example**:  
  - Black pawn on D4.  
  - White pawn moves from E2 → E4.  
  - On the next move, Black can capture the White pawn en passant by moving D4 → E3.  

---

## 🖥️ Graphical User Interface (GUI) & Game Flow
The project includes a fully interactive Java Swing GUI designed for an intuitive game experience.

### Game Orientation
- The board is always oriented with **White at the bottom**.  
- The game **always starts with White’s turn**.  

### Turn & Move Handling
- Players alternate turns, with **White moving first**.  
- Each player has **5 minutes total** to make their moves (countdown timer).  
- To make a move:  
  1. The current player **selects a piece**.  
  2. The GUI highlights all legal destination squares.  
  3. The player selects one of the highlighted squares to move the piece.  

### Toolbar Features
- **Restart Game**: Reset the board to the initial setup.  
- **Undo Last Move**: Revert the most recent move.  
- **Exit**: Quit the application.  

### Winning Conditions
- A player wins by:  
  - **Checkmate** (opponent’s king is trapped), or  
  - **Timeout** (opponent’s clock reaches zero).  

- By default, after a win the game **automatically restarts**.  

---