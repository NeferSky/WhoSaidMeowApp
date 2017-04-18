package nefersky.whosaidmeowapp;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            createOldSoundPool();
        } else {
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        mCatSound = loadSound("cat.ogg");
        mChickenSound = loadSound("chicken.ogg");
        mCowSound = loadSound("cow.ogg");
        mDogSound = loadSound("dog.ogg");
        mDuckSound = loadSound("duck.ogg");
        mSheepSound = loadSound("sheep.ogg");

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCat:
                        playSound(mCatSound);
                        break;
                    case R.id.btnChicken:
                        playSound(mChickenSound);
                        break;
                    case R.id.btnCow:
                        playSound(mCowSound);
                        break;
                    case R.id.btnDog:
                        playSound(mDogSound);
                        break;
                    case R.id.btnDuck:
                        playSound(mDuckSound);
                        break;
                    case R.id.btnSheep:
                        playSound(mSheepSound);
                        break;
                }
            }
        };

        ImageButton btnCat = (ImageButton)findViewById(R.id.btnCat);
        btnCat.setOnClickListener(onClickListener);
        ImageButton btnChicken = (ImageButton)findViewById(R.id.btnChicken);
        btnChicken.setOnClickListener(onClickListener);
        ImageButton btnCow = (ImageButton)findViewById(R.id.btnCow);
        btnCow.setOnClickListener(onClickListener);
        ImageButton btnDog = (ImageButton)findViewById(R.id.btnDog);
        btnDog.setOnClickListener(onClickListener);
        ImageButton btnDuck = (ImageButton)findViewById(R.id.btnDuck);
        btnDuck.setOnClickListener(onClickListener);
        ImageButton btnSheep = (ImageButton)findViewById(R.id.btnSheep);
        btnSheep.setOnClickListener(onClickListener);
    }

    private int playSound(int SoundID) {
        if (SoundID > 0) {
            mStreamID = mSoundPool.play(SoundID, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName, Toast.LENGTH_LONG).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            createOldSoundPool();
        } else {
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        mCatSound = loadSound("cat.ogg");
        mChickenSound = loadSound("chicken.ogg");
        mCowSound = loadSound("cow.ogg");
        mDogSound = loadSound("dog.ogg");
        mDuckSound = loadSound("duck.ogg");
        mSheepSound = loadSound("sheep.ogg");
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool(){
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }
}
