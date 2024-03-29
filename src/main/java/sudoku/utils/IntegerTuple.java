package sudoku.utils;

public class IntegerTuple {
  public IntegerTuple(int x, int y) {
    this.x = x;
    this.y = y;
  }
  @Override
  public int hashCode() {
    int hash = 17;
    hash = 31 * hash + this.x;
    hash = 31 * hash + this.y;
    return hash;
  }
  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null) return false;
    if (this.getClass() != other.getClass()) return false;
    IntegerTuple that = (IntegerTuple) other;
    return (this.x == that.x) && (this.y == that.y);
  }
  private int x;
  private int y;
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
}
