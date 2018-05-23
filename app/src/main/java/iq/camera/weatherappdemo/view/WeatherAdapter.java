package iq.camera.weatherappdemo.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import iq.camera.weatherappdemo.R;
import iq.camera.weatherappdemo.databinding.RecyclerItemBinding;
import iq.camera.weatherappdemo.databinding.RecyclerItemFirstDayBinding;
import iq.camera.weatherappdemo.model.DayItem;
import iq.camera.weatherappdemo.viewmodel.DayItemViewModel;
import iq.camera.weatherappdemo.viewmodel.FirstDayViewModel;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherAdapterViewHolder> {

    private List<DayItem> weatherList;

    public WeatherAdapter() {
        this.weatherList = Collections.emptyList();
    }


    @NonNull
    @Override
    public WeatherAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            RecyclerItemFirstDayBinding firstDayBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item_first_day, parent, false);

            return new WeatherAdapterViewHolder(firstDayBinding);

        } else {

            RecyclerItemBinding recyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item, parent, false);

            return new WeatherAdapterViewHolder(recyclerItemBinding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapterViewHolder holder, int position) {
        if (position == 0) {
            holder.bindDayItem(0, weatherList.get(position));
        } else {
            holder.bindDayItem(1, weatherList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return  0;
        }
        return 1;
    }

    public void setWeatherList(List<DayItem> weatherList){
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }


    public class WeatherAdapterViewHolder extends RecyclerView.ViewHolder {

        RecyclerItemBinding recyclerItemBinding;
        RecyclerItemFirstDayBinding firstDayBinding;

        public WeatherAdapterViewHolder(RecyclerItemFirstDayBinding firstDayBinding) {
            super(firstDayBinding.cv);
            this.firstDayBinding = firstDayBinding;
        }

        public WeatherAdapterViewHolder(RecyclerItemBinding recyclerItemBinding) {
            super(recyclerItemBinding.cv);
            this.recyclerItemBinding = recyclerItemBinding;
        }

        void bindDayItem(int viewType, DayItem dayItem){

            if (viewType == 0) {
                if (firstDayBinding.getFirstDayViewModel() == null) {
                    firstDayBinding.setFirstDayViewModel(
                            new FirstDayViewModel(dayItem, itemView.getContext()));
                } else {
                    firstDayBinding.getFirstDayViewModel().setDay(dayItem);
                }
                firstDayBinding.getFirstDayViewModel().addObserver(new Observer() {
                    @Override
                    public void update(Observable observable, Object o) {
                        if ( o instanceof ToggleButton) {
                            firstDayBinding.tempMax.setText(firstDayBinding.getFirstDayViewModel().getCurrentTemp());
                            updateUI();
                        }
                    }
                });
            } else {
                if (recyclerItemBinding.getDayItemViewModel() == null) {
                    recyclerItemBinding.setDayItemViewModel(
                            new DayItemViewModel(dayItem, itemView.getContext()));
                } else {
                    recyclerItemBinding.getDayItemViewModel().setDay(dayItem);
                }

            }



        }

    }

    public void updateUI() {
        notifyDataSetChanged();
    }

}
