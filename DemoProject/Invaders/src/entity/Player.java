package entity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {
    private static final long serialVersionUID = 9L;
    private static Color[] color = new Color[]{Color.GREEN, Color.CYAN, Color.ORANGE};
    public static Color PlayerColor(int i){
        if( i >= color.length){
            Random random = new Random();
            Color temp[] = new Color[color.length+1];
            int index = 0;
            for(index = 0 ; index < color.length; index++)
                temp[index] = color[index];
            temp[index] = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            color = temp;
        }
        return color[i];
    }

    /** Player's ship. */
    private Ship ship;
    /** Current score. */
    private int score;
    /** Player lives left. */
    private int lives;
    /** Total bullets shot by the player. */
    private int bulletsShot;
    /** Total ships destroyed by the player. */
    private int shipsDestroyed;

    private boolean die;

    public boolean isClient() {
        return isClient;
    }
    private boolean isClient = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private int inputs[];

    public Player(){
        this("Player1",0,3,new int[]{KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_SPACE});
    }
    public Player(String name, int score, int lives, int[] inputs){
        this(name,score,lives,inputs,false);
    }
    public Player(String name, int score, int lives, int[] inputs, boolean client){
        this.name = name;
        this.score = score;
        this.lives = lives;
        this.inputs = inputs;
        this.isClient = client;
        die = false;
    }
    public void Init(Ship ship, int score, int lives, int[] inputs){
        this.ship = ship;
        this.score = score;
        this.lives = lives;
        this.inputs = inputs;
    }

    //갱신
    public void update( ){

        this.ship.update();
    }

    //점수 추가
    public int addScore(int addscore){
        this.score += addscore;
        return this.score;
    }
    //목숨 증가
    public int addlive(int addlive){
        this.lives += addlive;
        return this.lives;
    }
    //목숨 감소
    public int subtractlive(int subtractlive){
        this.lives -= subtractlive;
        if( this.lives <= 0 )
        {
            System.out.println(name+" Destroyed");
            Die();
        }
        return this.lives;
    }
    //총알 발사 수 증가
    public int addbulletsShot(int bulletsShot){
        this.bulletsShot+=bulletsShot;
        return this.bulletsShot;
    }
    //총알 파괴 수 증가
    public int addshipsDestroyed(int shipsDestroyed){
        this.shipsDestroyed+=shipsDestroyed;
        return this.shipsDestroyed;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getBulletsShot() {
        return bulletsShot;
    }

    public void setBulletsShot(int bulletsShot) {
        this.bulletsShot = bulletsShot;
    }

    public int getShipsDestroyed() {
        return shipsDestroyed;
    }

    public void setShipsDestroyed(int shipsDestroyed) {
        this.shipsDestroyed = shipsDestroyed;
    }

    public int[] getInputs() {
        return inputs;
    }

    public void setInputs(int[] input) {
        this.inputs = input;
    }

    public boolean isDie(){return die;}
    public void Die(){die = true;}

    public void setDie(boolean die) {
        this.die = die;
    }
}
