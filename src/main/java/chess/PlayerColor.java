package chess;

public enum PlayerColor {
  WHITE, BLACK;

  public PlayerColor opposite() {
    return this == WHITE ? BLACK : WHITE;
  }
}
