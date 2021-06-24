package DonatorBottomNavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.donapp.AccountUpdate;
import com.example.donapp.Delete_Dialog;
import com.example.donapp.DonatorMainScreen;
import com.example.donapp.LoginPage;
import com.example.donapp.R;

public class SettingsFragment extends Fragment {

    public static final String TAG = "SettingsFragment";
    Button deleteAccount;
    Button updateAccount ;
    Button logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        //LOGOUT BUTTON (TAKES THE USER TO THE MAIN PAGE)

        deleteAccount = (Button)view.findViewById(R.id.buttonDeleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        logout = (Button)view.findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), LoginPage.class);
                startActivity(in);
            }
        });
        //UPDATE ACCOUNT BUTTON (TAKES THE USER TO THE ACCOUNT UPDATE PAGE)
        updateAccount = (Button)view.findViewById(R.id.buttonChangeInformation);
        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), AccountUpdate.class);
                startActivity(in);
            }
        });

        return view;

    }

    private void openDialog() {
        Delete_Dialog dialog = new Delete_Dialog();
        dialog.show(getFragmentManager(), "example dialog");
    }

    // SHOW THE NAME OF THE USER IN  THE SETTING PAGE
    public void onStart(){
        super.onStart();
        View view = getView();
        if (view!=null){
            TextView title = view.findViewById(R.id.textViewAccountUserDonator);
            title.setText(DonatorMainScreen.AccountUserDonator);
        }
    }

}

