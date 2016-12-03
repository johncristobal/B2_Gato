package vera.moon.com.b3_gato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //gameActive
    public boolean gameActive = true;

    //0 = yellow, 1 = red
    public int activePlayer = 0;
    //2 = unplayed
    public int [] gameState= {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropin(View v){

        //Recupero boton
        ImageView counter = (ImageView) v;

        //get tag
        String tag = counter.getTag().toString();
        int tagint = Integer.parseInt(tag);


        if (gameState[tagint] == 2 && gameActive==true){

            gameState[tagint] = activePlayer;
            //lo lanzo fuera
            counter.setTranslationY(-1000f);

            if(activePlayer == 0)
            {
                //Cambio imagens
                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;
            }
            else{
                //Cambio imagens
                counter.setImageResource(R.drawable.red);

                activePlayer = 0;
            }

            //Regreso con animacion
            counter.animate().translationYBy(1000f).setDuration(300);

            for (int [] winning: winningPositions){

                if (gameState[winning[0]] == gameState[winning[1]] &&
                        gameState[winning[1]] == gameState[winning[2]] &&
                        gameState[winning[0]] != 2){

                    gameActive = false;

                    String winn="Red";
                    if (gameState[winning[0]] == 0){
                        winn = "Yellow";
                    }

                    //Someone has won
                    TextView win = (TextView)findViewById(R.id.winnerMesa);
                    win.setText(winn + " has won!");

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainlayout);
                    layout.setVisibility(View.VISIBLE);
                }else{
                    boolean gameOver = true;

                    for(int count : gameState){
                        if(count == 2)
                            gameOver = false;
                    }

                    if (gameOver) {
                        gameActive = false;
                        String winn = "It's a draw";

                        //Someone has won
                        TextView win = (TextView) findViewById(R.id.winnerMesa);
                        win.setText(winn);

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainlayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View v){

        //Resstablecer toas las varaibles
        gameActive = true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainlayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for(int i=0;i<gameState.length;i++){
            gameState[i] = 2;
        }

        //Reestablece imagenes dentro del layot de un golpe
        GridLayout grid = (GridLayout)findViewById(R.id.gridLayou);

        //Woooooooooooooooooow grid.getChildCount() para conocer el numero de views dento de otro view
        for(int i=0;i<grid.getChildCount();i++){
            ImageView imagen = ((ImageView)grid.getChildAt(i));
            imagen.setImageResource(0);
        }
    }
}
