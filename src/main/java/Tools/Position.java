package Tools;

public class Position {
    protected int x, y;
    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int[] getPos(){
        int[] pos = {this.x, this.y};
        return pos;
    }
    public void  setX(int new_x){
        this.x=new_x;
    }
    public void  setY(int new_y){
        this.y=new_y;
    }
    public void setPos(Position new_pos){
        this.x= new_pos.getX();
        this.y= new_pos.getY();
    }
    public void setPos(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "X : "+this.x+" & Y : "+this.y;
    }
}
