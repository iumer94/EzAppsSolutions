package sfs.com.ezappssolutions.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import sfs.com.ezappssolutions.CreateAccount;
import sfs.com.ezappssolutions.LLCInformation;
import sfs.com.ezappssolutions.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseEntityType.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseEntityType#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseEntityType extends Fragment implements View.OnClickListener {

    ListView mainListView;
    TextView maintittle,sole_propreitor,llc,trust,estate_diseased,non_profit,partnership,
            corporation,s_corporation,personal_service_corporation,
            church_controlled;

    SharedPreferences pref;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChooseEntityType() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseEntityType.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseEntityType newInstance(String param1, String param2) {
        ChooseEntityType fragment = new ChooseEntityType();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_choose_entity_type, container, false);
        maintittle=(TextView) rootView.findViewById(R.id.tv_title);
        sole_propreitor = (TextView) rootView.findViewById(R.id.sole_propreitor);
        llc = (TextView) rootView.findViewById(R.id.llc);
        non_profit = (TextView) rootView.findViewById(R.id.non_profit);
        partnership = (TextView) rootView.findViewById(R.id.partnership);
        corporation = (TextView) rootView.findViewById(R.id.corporation);
        s_corporation = (TextView) rootView.findViewById(R.id.s_corporation);

        sole_propreitor.setOnClickListener(this);
        llc.setOnClickListener(this);
        non_profit.setOnClickListener(this);
        partnership.setOnClickListener(this);
        corporation.setOnClickListener(this);
        s_corporation.setOnClickListener(this);

        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onClick(View view) {
        /*if(view.getId() == sole_propreitor.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "Sole Propretior");
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Sole Propretior");
                startActivity(intent);
            }




        }*/

        if (view.getId() == llc.getId()) {

            if (checkIfUserExist() == true) {

                Intent intent = new Intent(getActivity(), LLCInformation.class);
                intent.putExtra("tittle", "Limited Liability Company");
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Limited Liability Company");
                startActivity(intent);
            }


        }
    }

        /*if(view.getId() == trust.getId())
        {


            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), Trust_information.class);
                intent.putExtra("tittle", "Trust");
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);

                intent.putExtra("tittle", "Trust");
                startActivity(intent);
            }


        }

        if(view.getId() == estate_diseased.getId())
        {
            if(checkIfUserExist() == true)
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Estate of Deceased Individual");
                startActivity(intent);
            }


            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Estate of Deceased Individual");
                startActivity(intent);
            }


        }

        if(view.getId() == non_profit.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "Non Profit");
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);

                intent.putExtra("tittle", "Non Profit");
                startActivity(intent);
            }


        }

        if(view.getId() == partnership.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "Partnership");
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Partnership");
                startActivity(intent);

            }


        }

        if(view.getId() == corporation.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "Corporation");
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Corporation");
                startActivity(intent);
            }



        }

        if(view.getId() == s_corporation.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "S-Corporation");
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "S-Corporation");

                startActivity(intent);
            }



        }

        if(view.getId() == personal_service_corporation.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "Personal Service Corporation");
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);
                intent.putExtra("tittle", "Personal Service Corporation");
                startActivity(intent);
            }


        }


        if(view.getId() == church_controlled.getId())
        {

            if(checkIfUserExist() == true)
            {

                Intent intent = new Intent(getActivity(), BussinessInformation.class);
                intent.putExtra("tittle", "Church Controlled");
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(getActivity(), CreateAccount.class);

                intent.putExtra("tittle", "Church Controlled");
                startActivity(intent);
            }



        }

    }*/

    private boolean checkIfUserExist() {
        Boolean flag = true;
        pref = getContext().getSharedPreferences(getString(R.string.userSession), 0); // 0 - for private mode
        String name = pref.getString(getString(R.string.userName), null);
        String pass = pref.getString(getString(R.string.userPass), null);
        if (name == null || pass == null) {
            flag = false;
        }
        return flag;
    }
}
