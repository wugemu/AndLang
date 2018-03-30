package com.example.test.andlangtest.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.example.test.andlangtest.Presenter.BlackPresenter;
import com.example.test.andlangtest.ViewModel.BlackViewModel;
import com.example.test.andlangtest.R;

import java.util.Observable;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends BaseLangFragment<BlackPresenter> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BlackFragemtInterface listener;


    @BindView(R.id.iv_fragment)
    ImageView iv_fragment;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_blank;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        presenter=new BlackPresenter(this,activity,BlackViewModel.class);
    }


    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater,container,savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BlackFragemtInterface) {
            listener = (BlackFragemtInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface BlackFragemtInterface {
        // TODO: Update argument type and name
        public void setData(String value);
    }

    @Override
    public void update(Observable o, Object arg) {
        BlackViewModel model=(BlackViewModel)presenter.model;
        if((int)arg==1){
            ImageLoadUtils.doLoadImageUrl(iv_fragment,model.getIv_fragment());
        }
    }
}
