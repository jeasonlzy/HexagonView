package com.lzy.hexagonviewdemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.colorpicker.ColorPickerDialog;
import com.lzy.widget.HexagonView;

public class HexagonActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener, TextWatcher, View.OnClickListener {

    private static final int SELECT_PIC_KITKAT = 0;
    private static final int SELECT_PIC = 1;
    private TextView tv_textSize;
    private TextView tv_borderWidth;
    private TextView tv_corner;
    private TextView tv_breakLineCount;
    private TextView tv_maxLine;
    private TextView tv_textSpacing;
    private TextView tv_width;
    private TextView tv_height;
    private TextView tv_padding;
    private HexagonView hexagon;
    ViewGroup.LayoutParams params;

    private int textColor = 0xFFFFFFFF;
    private int borderColor = 0x99FFFF00;
    private int fillColor = 0xFFFF0000;
    private int backColor = 0x00000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hexagon);

        hexagon = (HexagonView) findViewById(R.id.hexagon);

        tv_textSize = (TextView) findViewById(R.id.tv_textSize);
        tv_borderWidth = (TextView) findViewById(R.id.tv_borderWidth);
        tv_corner = (TextView) findViewById(R.id.tv_corner);
        tv_breakLineCount = (TextView) findViewById(R.id.tv_breakLineCount);
        tv_maxLine = (TextView) findViewById(R.id.tv_maxLine);
        tv_textSpacing = (TextView) findViewById(R.id.tv_textSpacing);
        tv_width = (TextView) findViewById(R.id.tv_width);
        tv_height = (TextView) findViewById(R.id.tv_height);
        tv_padding = (TextView) findViewById(R.id.tv_padding);

        SeekBar sb_textSize = (SeekBar) findViewById(R.id.sb_textSize);
        sb_textSize.setOnSeekBarChangeListener(this);
        sb_textSize.setMax(50);
        sb_textSize.setProgress(20);
        SeekBar sb_borderWidth = (SeekBar) findViewById(R.id.sb_borderWidth);
        sb_borderWidth.setOnSeekBarChangeListener(this);
        sb_borderWidth.setMax(50);
        sb_borderWidth.setProgress(20);
        SeekBar sb_corner = (SeekBar) findViewById(R.id.sb_corner);
        sb_corner.setOnSeekBarChangeListener(this);
        sb_corner.setMax(50);
        sb_corner.setProgress(10);
        SeekBar sb_breakLineCount = (SeekBar) findViewById(R.id.sb_breakLineCount);
        sb_breakLineCount.setOnSeekBarChangeListener(this);
        sb_breakLineCount.setMax(15);
        sb_breakLineCount.setProgress(6);
        SeekBar sb_maxLine = (SeekBar) findViewById(R.id.sb_maxLine);
        sb_maxLine.setOnSeekBarChangeListener(this);
        sb_maxLine.setMax(5);
        sb_maxLine.setProgress(3);
        SeekBar sb_textSpacing = (SeekBar) findViewById(R.id.sb_textSpacing);
        sb_textSpacing.setOnSeekBarChangeListener(this);
        sb_textSpacing.setMax(20);
        sb_textSpacing.setProgress(0);
        SeekBar sb_width = (SeekBar) findViewById(R.id.sb_width);
        sb_width.setOnSeekBarChangeListener(this);
        sb_width.setMax(300);
        sb_width.setProgress(200);
        SeekBar sb_height = (SeekBar) findViewById(R.id.sb_height);
        sb_height.setOnSeekBarChangeListener(this);
        sb_height.setMax(300);
        sb_height.setProgress(200);
        SeekBar sb_padding = (SeekBar) findViewById(R.id.sb_padding);
        sb_padding.setOnSeekBarChangeListener(this);
        sb_padding.setMax(100);
        sb_padding.setProgress(10);

        RadioGroup orientation = (RadioGroup) findViewById(R.id.orientation);
        orientation.setOnCheckedChangeListener(this);
        orientation.check(R.id.vertical);

        CheckBox borderOverlay = (CheckBox) findViewById(R.id.borderOverlay);
        borderOverlay.setOnCheckedChangeListener(this);
        borderOverlay.setChecked(false);
        CheckBox isUseBitmap = (CheckBox) findViewById(R.id.isUseBitmap);
        isUseBitmap.setOnCheckedChangeListener(this);
        isUseBitmap.setChecked(true);
        CheckBox enableClick = (CheckBox) findViewById(R.id.enableClick);
        enableClick.setOnCheckedChangeListener(this);
        enableClick.setChecked(true);

        Button checkImage = (Button) findViewById(R.id.checkImage);
        checkImage.setOnClickListener(this);
        Button textColor = (Button) findViewById(R.id.textColor);
        textColor.setOnClickListener(this);
        Button borderColor = (Button) findViewById(R.id.borderColor);
        borderColor.setOnClickListener(this);
        Button fillColor = (Button) findViewById(R.id.fillColor);
        fillColor.setOnClickListener(this);
        Button backColor = (Button) findViewById(R.id.backColor);
        backColor.setOnClickListener(this);

        EditText text = (EditText) findViewById(R.id.text);
        text.addTextChangedListener(this);
        text.setText("ABC这是EFG测试文字H");
        text.selectAll();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_textSize:
                hexagon.setTextSize(progress);
                tv_textSize.setText(progress + "sp");
                break;
            case R.id.sb_borderWidth:
                hexagon.setBorderWidth((int) (progress * getResources().getDisplayMetrics().density));
                tv_borderWidth.setText(progress + "dp");
                break;
            case R.id.sb_corner:
                hexagon.setCorner((int) (progress * getResources().getDisplayMetrics().density));
                tv_corner.setText(progress + "dp");
                break;
            case R.id.sb_breakLineCount:
                hexagon.setBreakLineCount(progress);
                tv_breakLineCount.setText(progress + "");
                break;
            case R.id.sb_maxLine:
                hexagon.setMaxLine(progress);
                tv_maxLine.setText(progress + "");
                break;
            case R.id.sb_textSpacing:
                hexagon.setTextSpacing((int) (progress * getResources().getDisplayMetrics().density));
                tv_textSpacing.setText(progress + "dp");
                break;
            case R.id.sb_width:
                params = hexagon.getLayoutParams();
                params.width = (int) (progress * getResources().getDisplayMetrics().density);
                hexagon.setLayoutParams(params);
                tv_width.setText(progress + "dp");
                break;
            case R.id.sb_height:
                params = hexagon.getLayoutParams();
                params.height = (int) (progress * getResources().getDisplayMetrics().density);
                hexagon.setLayoutParams(params);
                tv_height.setText(progress + "dp");
                break;
            case R.id.sb_padding:
                int padding = (int) (progress * getResources().getDisplayMetrics().density);
                hexagon.setPadding(padding, padding, padding, padding);
                tv_padding.setText(progress + "dp");
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.horizontal:
                hexagon.setHexagonOrientation(HexagonView.HORIZONTAL);
                break;
            case R.id.vertical:
                hexagon.setHexagonOrientation(HexagonView.VERTICAL);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.borderOverlay:
                hexagon.setBorderOverlay(isChecked);
                break;
            case R.id.isUseBitmap:
                if (isChecked) {
                    hexagon.setImageResource(R.mipmap.hexagon_image);
                } else {
                    hexagon.setImageBitmap(null);
                }
                break;
            case R.id.enableClick:
                if (isChecked) {
                    hexagon.setOnHexagonClickListener(new HexagonView.OnHexagonViewClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(HexagonActivity.this, "点击六边形了！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    hexagon.setOnHexagonClickListener(null);
                }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        hexagon.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textColor:
                ColorPickerDialog textDialog = new ColorPickerDialog(this, textColor);
                textDialog.setAlphaSliderVisible(true);
                textDialog.setHexValueEnabled(true);
                textDialog.show();
                textDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color) {
                        textColor = color;
                        hexagon.setTextColor(color);
                    }
                });
                break;
            case R.id.borderColor:
                ColorPickerDialog borderDialog = new ColorPickerDialog(this, borderColor);
                borderDialog.setAlphaSliderVisible(true);
                borderDialog.setHexValueEnabled(true);
                borderDialog.show();
                borderDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color) {
                        borderColor = color;
                        hexagon.setBorderColor(color);
                    }
                });
                break;
            case R.id.fillColor:
                ColorPickerDialog fillColorDialog = new ColorPickerDialog(this, fillColor);
                fillColorDialog.setAlphaSliderVisible(true);
                fillColorDialog.setHexValueEnabled(true);
                fillColorDialog.show();
                fillColorDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color) {
                        fillColor = color;
                        hexagon.setFillColor(color);
                    }
                });
                break;
            case R.id.backColor:
                ColorPickerDialog backColorDialog = new ColorPickerDialog(this, backColor);
                backColorDialog.setAlphaSliderVisible(true);
                backColorDialog.setHexValueEnabled(true);
                backColorDialog.show();
                backColorDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color) {
                        backColor = color;
                        hexagon.setBackgroundColor(color);
                    }
                });
                break;
            case R.id.checkImage:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data && requestCode == 101) {
            Uri selectedImage = data.getData();     //    content://media/external/images/media/16753
            String path = selectedImage.getPath();  //    /external/images/media/16753
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            System.out.println("cursor:" + cursor);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);   //     /storage/emulated/0/DCIM/Camera/1450292407465.jpg
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                hexagon.setImageBitmap(bitmap);
            }
        }
    }
}