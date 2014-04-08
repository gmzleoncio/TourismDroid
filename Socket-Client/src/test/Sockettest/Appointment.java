package test.Sockettest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class Appointment extends Activity {
	private TextView tAppointment;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ventana);
		tAppointment = (TextView) findViewById(R.id.txtTurn);
		Intent intent = getIntent();
		String turno = intent.getStringExtra(Sockettest.APPOINT);
		tAppointment.setText(turno);
		
		
}

	
}