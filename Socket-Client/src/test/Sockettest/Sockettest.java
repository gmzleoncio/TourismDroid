package test.Sockettest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;


public class Sockettest extends Activity {
	public final static String APPOINT = "test.Sockettest.MESSAGE";
	
	/** Called when the activity is first created. */

	private Button btconect, btdisconect, btnRegular, btnPremium;
	Socket miCliente;
	private VideoView mVideoView;
	DataOutputStream dos;
	DataInputStream dis;
	String received;
	String appo;
	String curr;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btconect = (Button) findViewById(R.id.btnconexion);
		btdisconect = (Button) findViewById(R.id.btdisc);
		btnRegular = (Button) findViewById(R.id.newRegular);
		btnPremium = (Button) findViewById(R.id.newPremium);
		mVideoView = (VideoView)findViewById(R.id.videoView1);
		mVideoView.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=tSB9Z1QZSlg"));
		mVideoView.start();
		mVideoView.requestFocus();
		
		btconect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				boolean conectstatus = Connect();
				Context contexto = getApplicationContext();
				
				if (conectstatus) { 
					Toast t = Toast.makeText(contexto, "Conectado", Toast.LENGTH_LONG);
					t.show();
					

				} else {
					Toast t = Toast.makeText(contexto, "NO Conectado", Toast.LENGTH_LONG);
					t.show();
				}
			}
		});
		
		btdisconect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Disconnect();
			}
		});

		btnRegular.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Snd_txt_Msg("1");
				Intent intent = new Intent(Sockettest.this, Appointment.class);
				appo = received;
				intent.putExtra(APPOINT, appo);
				startActivity(intent);
				
			}
		});
		
		btnPremium.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Snd_txt_Msg("2");
				Intent intent = new Intent(Sockettest.this, Appointment.class);
				appo = received;
				intent.putExtra(APPOINT, appo);
				startActivity(intent);
			}
		});
	}

	public boolean Connect() {
		
		String IP = "10.0.2.2";
		int PORT = 7060;

		try {
			miCliente = new Socket(IP, PORT);
			
			if (miCliente.isConnected() == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.e("Error connect()", "" + e);
			return false;
		}
	}

	public void Disconnect() {
		try {
			miCliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Snd_txt_Msg(String txt) {

		boolean val_acc = Snd_Msg(txt);
		
		if (!val_acc) {
			Log.e("Snd_txt_Msg() -> ", "!ERROR!");
		}
	}

	public boolean Snd_Msg(String msg) {

		try {
			dos = new DataOutputStream(miCliente.getOutputStream());
			dis = new DataInputStream(new DataInputStream(miCliente.getInputStream()));
			
		
			if (miCliente.isConnected()){
				dos.writeUTF(msg);
				received = dis.readUTF();
				return true;
			} else {
				return false;
			}

		} catch (IOException e) {
			Log.e("Snd_Msg() ERROR -> ", "" + e);

			return false;
		}
	}
}