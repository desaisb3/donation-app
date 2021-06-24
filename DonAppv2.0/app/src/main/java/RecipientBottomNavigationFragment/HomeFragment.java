package RecipientBottomNavigationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.donapp.R;
import com.example.donapp.ShowImages;

public class HomeFragment extends Fragment {

    private Button showImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rfragment_home,container,false);

        showImage = v.findViewById(R.id.showImageBttn);

        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAvailableDonations();
            }
        });
        return v;
    }

    public void openAvailableDonations(){
        Intent intent = new Intent(getActivity(), ShowImages.class);
        startActivity(intent);
    }

}
