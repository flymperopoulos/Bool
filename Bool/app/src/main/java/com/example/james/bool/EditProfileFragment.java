package com.example.james.bool;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by flymperopoulos on 11/5/2014.
 */
public class EditProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    static final int SELECT_IMAGE = 0;

    private Context context;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    private Bitmap scaleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                    Bitmap bm = scaleBitmap(bitmap);
                    ImageView img = (ImageView) getView().findViewById(R.id.personImage);
                    img.setImageBitmap(bm);
                    img.postInvalidate();
                }
                catch(Exception e)
                {
                    Log.wtf("MyApp", "Something went wrong with bitmap!", e);
                }
            }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.editprofile_fragment, container, false);

        // Spinners initialized
        Spinner spinnerRace = (Spinner) rootView.findViewById(R.id.spinnerRace);
        Spinner spinnerOccupation = (Spinner) rootView.findViewById(R.id.spinnerOccupation);
        Spinner spinnerState = (Spinner) rootView.findViewById(R.id.spinnerState);

        Button cont = (Button) rootView.findViewById(R.id.continue_button);
        Button editprof = (Button) rootView.findViewById(R.id.edit_button);
        ImageView img = (ImageView) rootView.findViewById(R.id.personImage);
        img.setImageDrawable(getResources().getDrawable(R.drawable.editproff));

        final EditText FirstName = (EditText) rootView.findViewById(R.id.firstname);
        final EditText LastName = (EditText) rootView.findViewById(R.id.lastname);

        // Adapters for spinners defined
        ArrayAdapter<CharSequence> adapterRace = ArrayAdapter.createFromResource(getActivity(),
                R.array.races, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterOccupation = ArrayAdapter.createFromResource(getActivity(),
                R.array.occupations, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterState = ArrayAdapter.createFromResource(getActivity(),
                R.array.states, android.R.layout.simple_spinner_item);


        adapterRace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRace.setAdapter(adapterRace);
        spinnerRace.setOnItemSelectedListener(this);

        adapterOccupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOccupation.setAdapter(adapterOccupation);
        spinnerOccupation.setOnItemSelectedListener(this);

        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapterState);
        spinnerState.setOnItemSelectedListener(this);

        final RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativelayout);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == relativeLayout) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(FirstName.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(LastName.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirstName.getText().toString().isEmpty() || LastName.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter your full name correctly!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "Welcome, " + FirstName.getText().toString() + "!", Toast.LENGTH_SHORT).show();

                final MyActivity activity = (MyActivity) getActivity();
                final String first = FirstName.getText().toString();
                final String last = LastName.getText().toString();
                Intent BeginMain = new Intent("android.intent.action.LATERMAIN");
                startActivity(BeginMain);
            }
        });

        editprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Set a Profile Picture");
                alert.setPositiveButton("Choose from Library", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue accessing library
                        startActivityForResult(new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), SELECT_IMAGE);
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        return rootView;


    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}

