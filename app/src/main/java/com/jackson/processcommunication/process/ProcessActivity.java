package com.jackson.processcommunication.process;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jackson.processcommunication.BaseActivity;
import com.jackson.processcommunication.R;
import com.jackson.processcommunication.serializable.Book;
import com.jackson.processcommunication.serializable.User;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.jackson.processcommunication.PermissionUtils.RCODE_UM_SHARE;
import static com.jackson.processcommunication.PermissionUtils.shareRequestPermissions;

public class ProcessActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    /**
     * 使用Process开启多线程
     */

    @BindView(R.id.btn_pri_remote)
    Button btnPriRemote;
    @BindView(R.id.btn_global_remote)
    Button btnGlobalRemote;

    public static String path = Environment.getExternalStorageDirectory().getPath() + "/commu/user/";  // 文件保存路径

    @OnClick({R.id.btn_pri_remote, R.id.btn_global_remote})
    public void onClickListen(View view) {
        switch (view.getId()) {
            case R.id.btn_pri_remote:  //  验证Serializable
                Intent intent = new Intent(ProcessActivity.this, SecondActivity.class);
                User user = new User(101, "jack", true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_global_remote:  // 验证Parcelable
                Intent intent_parce=new Intent(ProcessActivity.this, ThirdActivity.class);
                intent_parce.putExtra("book",new Book(123,"Android",false));
                startActivity(intent_parce);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        ButterKnife.bind(this);
        applyPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 序列化,保存到文件中
     */
    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(100, "hello world", false);
                File dir = new File(path);
                Log.d("hbj",dir.exists()+"");

                if (!dir.exists()) {
                  dir.mkdirs();
                }

                File userfile = new File(path + "serialize");
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(userfile));
                    objectOutputStream.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    close(objectOutputStream);

                }


            }
        }).start();
    }


    public void close(Closeable closeable){

        try {
            if (closeable!=null){
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @AfterPermissionGranted(RCODE_UM_SHARE)
    private void applyPermission() {
        if (hasCameraPermission(shareRequestPermissions)) {
            persistToFile();
        } else {
            //申请权限
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_share_hint),
                    RCODE_UM_SHARE,
                    shareRequestPermissions);
        }
    }







    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


}
