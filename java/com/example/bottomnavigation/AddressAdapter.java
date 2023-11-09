package com.example.bottomnavigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<CustomAddress> addressList;
    private OnItemClickListener clickListener;
    private CustomAddress selectedAddress;
    private boolean showRadioButton; // Indicates whether to show the radio button

    public interface OnItemClickListener {
        void onItemClick(CustomAddress address);
    }

    public AddressAdapter(OnItemClickListener listener, boolean showRadioButton) {
        addressList = new ArrayList<>();
        clickListener = listener;
        this.showRadioButton = showRadioButton; // Set whether to show the radio button
    }

    public CustomAddress getSelectedAddress() {
        return selectedAddress;
    }

    public void addAddress(CustomAddress address) {
        addressList.add(address);
        notifyDataSetChanged();
    }

    public void clear() {
        addressList.clear();
        notifyDataSetChanged();
    }

    public void selectAddress(CustomAddress address) {
        selectedAddress = address;
        notifyDataSetChanged();
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        CustomAddress address = addressList.get(position);
        holder.addressTextView.setText(address.getAddress());
        holder.mobileNumberTextView.setText(address.getMobileNumber()); // Display mobile number

        if (showRadioButton) {
            holder.radioButtonAddress.setVisibility(View.VISIBLE);
            holder.radioButtonAddress.setChecked(address.equals(selectedAddress));
        } else {
            holder.radioButtonAddress.setVisibility(View.GONE); // Hide the radio button
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButtonAddress;
        TextView addressTextView;
        TextView mobileNumberTextView;

        AddressViewHolder(View itemView) {
            super(itemView);
            radioButtonAddress = itemView.findViewById(R.id.radioButtonAddress);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            mobileNumberTextView = itemView.findViewById(R.id.mobileNumberTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        CustomAddress clickedAddress = addressList.get(position);
                        selectAddress(clickedAddress);
                        clickListener.onItemClick(clickedAddress);
                    }
                }
            });
        }
    }
}
