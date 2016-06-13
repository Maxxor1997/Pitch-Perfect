//my name is daniel gao
//precondition: players must not be retarded 
public class Player {
    
    public String name = "daniel gao";
    private int score ;
    private Note guess ;
    
    public Player(){
        score=0;
        guess=null;
    }
    public Player(String name){
        score = 0;
        this.name=name;
        guess=null;
        System.out.println(name + " created");
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public Note getGuess(){
        return(guess);
    }
            
    public void setGuess(Note N){
        guess=N;
    }
    public void Taunt() {
        System.out.println("REkt");
    }
    
    
}
