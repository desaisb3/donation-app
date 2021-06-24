package DonatorBottomNavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.donapp.MakeADonation;
import com.example.donapp.R;

public class HomeFragment extends Fragment {

    //CREATING THE UPLOAD BUTTON
    private Button uploadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //USING VIEW V TO SHOW ON DEFAULT WHEN THE USER LOGINS ON THE HOME PAGE
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        //USING ABOVE STATEMENT WE CAN ACCESS THE ELEMENTS CREATED IN THE FRAGMENT
        uploadButton = v.findViewById(R.id.uploadBttn);

        //WHEN THE USER CLICKS ON THE UPLOAD BUTTON
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMakeADonation();
            }
        });
        //RETURN VIEW V THAT NEEDS TO BE SHOWN
        return v;
    }

    //INTENT TO CHANGE FROM HOME FRAGMENT TO CREATE DONATION PAGE..
    public void openMakeADonation(){
        Intent intent = new Intent(getActivity(),MakeADonation.class);
        startActivity(intent);
    }
}
