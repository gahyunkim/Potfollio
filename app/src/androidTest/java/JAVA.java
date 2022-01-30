import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class JAVA {
//
//    static final int REQUEST_CODE = 1;
//    ImageView imageView;
//    Uri uri;
//
//    public void onClickButton1(View view){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        startActivityForResult(intent, REQUEST_CODE);
//    }
//
//    private void startActivityForResult(Intent intent, int requestCode) {
//    }
//
//    @Override
//    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if (requestCode == REQUEST_CODE) {
//            uri = data.getData();
//        }
//    }
//    private void setImage(Uri uri) {
//        try{
//            InputStream in = getContentResolver().openInputStream(uri);
//            Bitmap bitmap = BitmapFactory.decodeStream(in);
//            imageView.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//    }
//
//    private ContentResolver getContentResolver() {
//    }
}
