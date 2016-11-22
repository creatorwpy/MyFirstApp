package library.mlibrary.view.wheelview.wheelview_one;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import library.mlibrary.R;
import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.view.wheelview.wheelview_one.adapter.ArrayWheelAdapter;
import library.mlibrary.view.wheelview.wheelview_one.widget.WheelView;

/**
 * Created by harmy on 2016/10/18 0018.
 */

public class TimePickWheelView extends RelativeLayout {
    public TimePickWheelView(Context context) {
        super(context);
        init();
    }

    public TimePickWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimePickWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public TimePickWheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private WheelView yearView;
    private TextView yearText;
    private boolean yearVisible = true;
    private WheelView monthView;
    private TextView monthText;
    private boolean monthVisible = true;
    private WheelView dayView;
    private TextView dayText;
    private boolean dayVisible = true;
    private WheelView hourView;
    private TextView hourText;
    private boolean hourVisible = true;
    private WheelView minuteView;
    private TextView minuteText;
    private boolean minuteVisible = true;
    private WheelView secondView;
    private TextView secondText;
    private boolean secondVisible = true;

    private void init() {
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        seconds = new ArrayList<>();
        addWheelViews();
    }

    private void addWheelViews() {
        removeAllViews();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_timepickwheelview, null);
        addView(view);
        yearView = (WheelView) view.findViewById(R.id.wv_year);
        monthView = (WheelView) view.findViewById(R.id.wv_month);
        dayView = (WheelView) view.findViewById(R.id.wv_day);
        hourView = (WheelView) view.findViewById(R.id.wv_hour);
        minuteView = (WheelView) view.findViewById(R.id.wv_minute);
        secondView = (WheelView) view.findViewById(R.id.wv_second);
        yearText = (TextView) view.findViewById(R.id.tv_year);
        monthText = (TextView) view.findViewById(R.id.tv_month);
        dayText = (TextView) view.findViewById(R.id.tv_day);
        hourText = (TextView) view.findViewById(R.id.tv_hour);
        minuteText = (TextView) view.findViewById(R.id.tv_minute);
        secondText = (TextView) view.findViewById(R.id.tv_second);
        if (yearVisible) {
            yearView.setVisibility(VISIBLE);
            yearText.setVisibility(VISIBLE);
        } else {
            yearView.setVisibility(GONE);
            yearText.setVisibility(GONE);
        }
        if (monthVisible) {
            monthView.setVisibility(VISIBLE);
            monthText.setVisibility(VISIBLE);
        } else {
            monthView.setVisibility(GONE);
            monthText.setVisibility(GONE);
        }
        if (dayVisible) {
            dayView.setVisibility(VISIBLE);
            dayText.setVisibility(VISIBLE);
        } else {
            dayView.setVisibility(GONE);
            dayText.setVisibility(GONE);
        }
        if (hourVisible) {
            hourView.setVisibility(VISIBLE);
            hourText.setVisibility(VISIBLE);
        } else {
            hourView.setVisibility(GONE);
            hourText.setVisibility(GONE);
        }
        if (minuteVisible) {
            minuteView.setVisibility(VISIBLE);
            minuteText.setVisibility(VISIBLE);
        } else {
            minuteView.setVisibility(GONE);
            minuteText.setVisibility(GONE);
        }
        if (secondVisible) {
            secondView.setVisibility(VISIBLE);
            secondText.setVisibility(VISIBLE);
        } else {
            secondView.setVisibility(GONE);
            secondText.setVisibility(GONE);
        }

        yearView.setLoop(wheelLoop);
        monthView.setLoop(wheelLoop);
        dayView.setLoop(wheelLoop);
        hourView.setLoop(wheelLoop);
        minuteView.setLoop(wheelLoop);
        secondView.setLoop(wheelLoop);

        int position = 0;
        initYear=true;
        //年
        years.clear();
        for (int i = startYear; i <= endYear; i++) {
            years.add(String.valueOf(i));
        }
        for (String value : years) {
            if (Integer.valueOf(value) == selectedYear) {
                break;
            }
            position++;
        }
        yearView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        yearView.setWheelData(years);

        yearView.setSelection(position);
        nowYear = years.get(yearView.getSelection());

        //月
        initMonth=true;
        onYearListener(true);

        //日
        initDay=true;
        onMonthListener(true);

        //时
        initHour=true;
        onDayListener(true);

        //分
        initMinute=true;
        onHourListener(true);

        //秒
        initSecond=true;
        onMinuteListener(true);

        yearView.setWheelSize(visibleNum);
        monthView.setWheelSize(visibleNum);
        dayView.setWheelSize(visibleNum);
        hourView.setWheelSize(visibleNum);
        minuteView.setWheelSize(visibleNum);
        secondView.setWheelSize(visibleNum);

        yearView.setStyle(style);
        monthView.setStyle(style);
        dayView.setStyle(style);
        hourView.setStyle(style);
        minuteView.setStyle(style);
        secondView.setStyle(style);

        yearView.setSkin(skin);
        monthView.setSkin(skin);
        dayView.setSkin(skin);
        hourView.setSkin(skin);
        minuteView.setSkin(skin);
        secondView.setSkin(skin);

        yearView.setOnWheelItemSelectedListener(yearListener);
        monthView.setOnWheelItemSelectedListener(monthListener);
        dayView.setOnWheelItemSelectedListener(dayListener);
        hourView.setOnWheelItemSelectedListener(hourListener);
        minuteView.setOnWheelItemSelectedListener(minuteListener);
        secondView.setOnWheelItemSelectedListener(secondListener);
    }

    private WheelView.OnWheelItemSelectedListener<String> yearListener = new WheelView.OnWheelItemSelectedListener<String>() {
        @Override
        public void onItemSelected(int position, String o) {
            if (initYear) {
                initYear = false;
                return;
            }
            nowYear = years.get(position);
            onYearListener(false);
        }
    };
    private WheelView.OnWheelItemSelectedListener<String> monthListener = new WheelView.OnWheelItemSelectedListener<String>() {
        @Override
        public void onItemSelected(int position, String o) {
            if (initMonth) {
                initMonth = false;
                return;
            }
            nowMonth = months.get(position);
            onMonthListener(false);
        }
    };
    private WheelView.OnWheelItemSelectedListener<String> dayListener = new WheelView.OnWheelItemSelectedListener<String>() {
        @Override
        public void onItemSelected(int position, String o) {
            if (initDay) {
                initDay = false;
                return;
            }
            nowDay = days.get(position);
            onDayListener(false);
        }
    };
    private WheelView.OnWheelItemSelectedListener<String> hourListener = new WheelView.OnWheelItemSelectedListener<String>() {
        @Override
        public void onItemSelected(int position, String o) {
            if (initHour) {
                initHour = false;
                return;
            }
            nowHour = hours.get(position);
            onHourListener(false);
        }
    };
    private WheelView.OnWheelItemSelectedListener<String> minuteListener = new WheelView.OnWheelItemSelectedListener<String>() {
        @Override
        public void onItemSelected(int position, String o) {
            if (initMinute) {
                initMinute = false;
                return;
            }
            nowMinute = minutes.get(position);
            onMinuteListener(false);
        }
    };
    private WheelView.OnWheelItemSelectedListener<String> secondListener = new WheelView.OnWheelItemSelectedListener<String>() {
        @Override
        public void onItemSelected(int position, String o) {
            if (initSecond) {
                initSecond = false;
                return;
            }
            nowSecond = seconds.get(position);
            onSecondListener(false);
        }
    };

    private boolean initYear = true;

    private void onYearListener(boolean init) {
        if (monthVisible) {
            if (years.size() == 1) {
                months.clear();
                for (int i = startMonth; i <= endMonth; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    months.add(value);
                }
            } else if (nowYear.equals(years.get(0))) {
                months.clear();
                for (int i = startMonth; i <= 12; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    months.add(value);
                }
            } else if (nowYear.equals(years.get(years.size() - 1))) {
                months.clear();
                for (int i = 1; i <= endMonth; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    months.add(value);
                }
            } else {
                months.clear();
                for (int i = 1; i <= 12; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    months.add(value);
                }
            }
            if (init) {
                int position = 0;
                for (String value : months) {
                    if (Integer.valueOf(value) == selectedMonth) {
                        break;
                    }
                    position++;
                }
                monthView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
                monthView.setWheelData(months);
                monthView.setSelection(position);
                nowMonth = months.get(monthView.getSelection());
            } else {
                int position = 0;
                for (String value : months) {
                    if (value.equals(nowMonth)) {
                        break;
                    }
                    position++;
                }
                monthView.setWheelData(months);
                if (position >= months.size()) {
                    position = months.size() - 1;
                }
                monthView.setSelection(position);
            }
        }
        if (!init) {
            onMonthListener(false);
        }
    }

    private boolean initMonth = true;

    private void onMonthListener(boolean init) {
        if (dayVisible) {
            if (months.size() == 1) {
                days.clear();
                switch (nowMonth) {
                    case "01":
                    case "03":
                    case "05":
                    case "07":
                    case "08":
                    case "10":
                    case "12":
                        for (int i = startDay; i <= endDay; i++) {
                            String value = String.valueOf(i);
                            if (value.length() == 1) {
                                value = "0" + value;
                            }
                            days.add(value);
                        }
                        break;
                    case "02":
                        int year = Integer.valueOf(nowYear);
                        if (year % 4 == 0) {
                            if (endDay <= 29) {
                                for (int i = startDay; i <= endDay; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            } else {
                                for (int i = startDay; i <= 29; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            }
                        } else {
                            if (endDay <= 28) {
                                for (int i = startDay; i <= endDay; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            } else {
                                for (int i = startDay; i <= 28; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            }
                        }
                        break;
                    case "04":
                    case "06":
                    case "09":
                    case "11":
                        if (endDay <= 30) {
                            for (int i = startDay; i <= endDay; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        } else {
                            for (int i = startDay; i <= 30; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        }
                        break;
                }
            } else if (nowMonth.equals(months.get(0)) && nowYear.equals(years.get(0))) {
                days.clear();
                switch (nowMonth) {
                    case "01":
                    case "03":
                    case "05":
                    case "07":
                    case "08":
                    case "10":
                    case "12":
                        for (int i = startDay; i <= 31; i++) {
                            String value = String.valueOf(i);
                            if (value.length() == 1) {
                                value = "0" + value;
                            }
                            days.add(value);
                        }
                        break;
                    case "02":
                        int year = Integer.valueOf(nowYear);
                        if (year % 4 == 0) {
                            for (int i = startDay; i <= 29; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        } else {
                            for (int i = startDay; i <= 28; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        }
                        break;
                    case "04":
                    case "06":
                    case "09":
                    case "11":
                        for (int i = startDay; i <= 30; i++) {
                            String value = String.valueOf(i);
                            if (value.length() == 1) {
                                value = "0" + value;
                            }
                            days.add(value);
                        }
                        break;
                }
            } else if (nowMonth.equals(months.get(months.size() - 1)) && nowYear.equals(years.get(years.size() - 1))) {
                days.clear();
                switch (nowMonth) {
                    case "01":
                    case "03":
                    case "05":
                    case "07":
                    case "08":
                    case "10":
                    case "12":
                        for (int i = 1; i <= endDay; i++) {
                            String value = String.valueOf(i);
                            if (value.length() == 1) {
                                value = "0" + value;
                            }
                            days.add(value);
                        }
                        break;
                    case "02":
                        int year = Integer.valueOf(nowYear);
                        if (year % 4 == 0) {
                            if (endDay <= 29) {
                                for (int i = 1; i <= endDay; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            } else {
                                for (int i = 1; i <= 29; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            }
                        } else {
                            if (endDay <= 28) {
                                for (int i = 1; i <= endDay; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            } else {
                                for (int i = 1; i <= 28; i++) {
                                    String value = String.valueOf(i);
                                    if (value.length() == 1) {
                                        value = "0" + value;
                                    }
                                    days.add(value);
                                }
                            }
                        }
                        break;
                    case "04":
                    case "06":
                    case "09":
                    case "11":
                        if (endDay <= 30) {
                            for (int i = 1; i <= endDay; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        } else {
                            for (int i = 1; i <= 30; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        }
                        break;
                }
            } else {
                days.clear();
                switch (nowMonth) {
                    case "01":
                    case "03":
                    case "05":
                    case "07":
                    case "08":
                    case "10":
                    case "12":
                        for (int i = 1; i <= 31; i++) {
                            String value = String.valueOf(i);
                            if (value.length() == 1) {
                                value = "0" + value;
                            }
                            days.add(value);
                        }
                        break;
                    case "02":
                        int year = Integer.valueOf(nowYear);
                        if (year % 4 == 0) {
                            for (int i = 1; i <= 29; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        } else {
                            for (int i = 1; i <= 28; i++) {
                                String value = String.valueOf(i);
                                if (value.length() == 1) {
                                    value = "0" + value;
                                }
                                days.add(value);
                            }
                        }
                        break;
                    case "04":
                    case "06":
                    case "09":
                    case "11":
                        for (int i = 1; i <= 30; i++) {
                            String value = String.valueOf(i);
                            if (value.length() == 1) {
                                value = "0" + value;
                            }
                            days.add(value);
                        }
                        break;
                }
            }
            if (init) {
                int position = 0;
                for (String value : days) {
                    if (Integer.valueOf(value) == selectedDay) {
                        break;
                    }
                    position++;
                }
                dayView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
                dayView.setWheelData(days);
                dayView.setSelection(position);
                nowDay = days.get(dayView.getSelection());
            } else {
                int position = 0;
                for (String value : days) {
                    if (value.equals(nowDay)) {
                        break;
                    }
                    position++;
                }
                dayView.setWheelData(days);
                if (position >= days.size()) {
                    position = days.size() - 1;
                }
                dayView.setSelection(position);
            }
        }
        if (!init) {
            onDayListener(false);
        }
    }

    private boolean initDay = true;

    private void onDayListener(boolean init) {
        if (hourVisible) {
            if (days.size() == 1) {
                hours.clear();
                for (int i = startHour; i <= endHour; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    hours.add(value);
                }
            } else if (nowDay.equals(days.get(0)) && nowMonth.equals(months.get(0)) && nowYear.equals(years.get(0))) {
                hours.clear();
                for (int i = startHour; i <= 23; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    hours.add(value);
                }
            } else if (nowDay.equals(days.get(days.size() - 1)) && nowMonth.equals(months.get(months.size() - 1)) && nowYear.equals(years.get(years.size() - 1))) {
                hours.clear();
                for (int i = 0; i <= endHour; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    hours.add(value);
                }
            } else {
                hours.clear();
                for (int i = 0; i <= 23; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    hours.add(value);
                }
            }
            if (init) {
                int position = 0;
                for (String value : hours) {
                    if (Integer.valueOf(value) == selectedHour) {
                        break;
                    }
                    position++;
                }
                hourView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
                hourView.setWheelData(hours);
                hourView.setSelection(position);
                nowHour = hours.get(hourView.getSelection());
            } else {
                int position = 0;
                for (String value : hours) {
                    if (value.equals(nowHour)) {
                        break;
                    }
                    position++;
                }
                hourView.setWheelData(hours);
                if (position >= hours.size()) {
                    position = hours.size() - 1;
                }
                hourView.setSelection(position);
            }
        }
        if (!init) {
            onHourListener(false);
        }
    }

    private boolean initHour = true;

    private void onHourListener(boolean init) {
        if (minuteVisible) {
            if (hours.size() == 1) {
                minutes.clear();
                for (int i = startMinute; i <= endMinute; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    minutes.add(value);
                }
            } else if (nowHour.equals(hours.get(0)) && nowDay.equals(days.get(0)) && nowMonth.equals(months.get(0)) && nowYear.equals(years.get(0))) {
                minutes.clear();
                for (int i = startMinute; i <= 59; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    minutes.add(value);
                }
            } else if (nowHour.equals(hours.get(hours.size() - 1)) && nowDay.equals(days.get(days.size() - 1)) && nowMonth.equals(months.get(months.size() - 1)) && nowYear.equals(years.get(years.size() - 1))) {
                minutes.clear();
                for (int i = 0; i <= endMinute; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    minutes.add(value);
                }
            } else {
                minutes.clear();
                for (int i = 0; i <= 59; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    minutes.add(value);
                }
            }
            if (init) {
                int position = 0;
                for (String value : minutes) {
                    if (Integer.valueOf(value) == selectedHour) {
                        break;
                    }
                    position++;
                }
                minuteView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
                minuteView.setWheelData(minutes);
                minuteView.setSelection(position);
                nowMinute = minutes.get(minuteView.getSelection());
            } else {
                int position = 0;
                for (String value : minutes) {
                    if (value.equals(nowMinute)) {
                        break;
                    }
                    position++;
                }
                minuteView.setWheelData(minutes);
                if (position >= minutes.size()) {
                    position = minutes.size() - 1;
                }
                minuteView.setSelection(position);
            }
        }
        if (!init) {
            onMinuteListener(false);
        }
    }

    private boolean initMinute = true;

    private void onMinuteListener(boolean init) {
        if (secondVisible) {
            if (minutes.size() == 1) {
                seconds.clear();
                for (int i = startSecond; i <= endSecond; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    seconds.add(value);
                }
            } else if (nowMinute.equals(minutes.get(0)) && nowHour.equals(hours.get(0)) && nowDay.equals(days.get(0)) && nowMonth.equals(months.get(0)) && nowYear.equals(years.get(0))) {
                seconds.clear();
                for (int i = startSecond; i <= 59; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    seconds.add(value);
                }
            } else if (nowMinute.equals(minutes.get(minutes.size() - 1)) && nowHour.equals(hours.get(hours.size() - 1)) && nowDay.equals(days.get(days.size() - 1)) && nowMonth.equals(months.get(months.size() - 1)) && nowYear.equals(years.get(years.size() - 1))) {
                seconds.clear();
                for (int i = 0; i <= endSecond; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    seconds.add(value);
                }
            } else {
                seconds.clear();
                for (int i = 0; i <= 59; i++) {
                    String value = String.valueOf(i);
                    if (value.length() == 1) {
                        value = "0" + value;
                    }
                    seconds.add(value);
                }
            }
            if (init) {
                int position = 0;
                for (String value : seconds) {
                    if (Integer.valueOf(value) == selectedHour) {
                        break;
                    }
                    position++;
                }
                secondView.setWheelAdapter(new ArrayWheelAdapter(getContext()));
                secondView.setWheelData(seconds);
                secondView.setSelection(position);
                nowSecond = seconds.get(secondView.getSelection());
            } else {
                int position = 0;
                for (String value : seconds) {
                    if (value.equals(nowSecond)) {
                        break;
                    }
                    position++;
                }
                secondView.setWheelData(seconds);
                if (position >= seconds.size()) {
                    position = seconds.size() - 1;
                }
                secondView.setSelection(position);
            }
        }
        if (!init) {
            onSecondListener(false);
        }
    }

    private boolean initSecond = true;

    private void onSecondListener(boolean init) {
        if (seconds.size() == 1) {

        } else if (nowSecond.equals(seconds.get(0)) && nowMinute.equals(minutes.get(0)) && nowHour.equals(hours.get(0)) && nowDay.equals(days.get(0)) && nowMonth.equals(months.get(0)) && nowYear.equals(years.get(0))) {

        } else if (nowSecond.equals(seconds.get(seconds.size() - 1)) && nowMinute.equals(minutes.get(minutes.size() - 1)) && nowHour.equals(hours.get(hours.size() - 1)) && nowDay.equals(days.get(days.size() - 1)) && nowMonth.equals(months.get(months.size() - 1)) && nowYear.equals(years.get(years.size() - 1))) {

        } else {

        }
    }

    /**
     * @param year    显示年
     * @param month   显示月
     * @param day     显示日
     * @param hour    显示时
     * @param munites 显示分
     * @param second  显示秒
     */
    public void setVisibleView(boolean year, boolean month, boolean day, boolean hour, boolean munites, boolean second) {
        yearVisible = year;
        monthVisible = month;
        dayVisible = day;
        hourVisible = hour;
        minuteVisible = munites;
        secondVisible = second;
    }

    /**
     * 绘制时间控件，改变控件属性后需要调用该方法使设置生效
     */
    public void draw() {
        addWheelViews();
    }

    private String nowYear;
    private String nowMonth;
    private String nowDay;
    private String nowHour;
    private String nowMinute;
    private String nowSecond;

    public long getSelectedTimeLong() {
        return CommonUtil.formatDate(getSelectedTimeString(), getFormat());
    }

    /**
     * @return 得到当前选中的时间
     */
    public String getSelectedTimeString() {
        String time = "";
        if (yearVisible) {
            time += nowYear + "年";
        }
        if (monthVisible) {
            time += nowMonth + "月";
        }
        if (dayVisible) {
            time += nowDay + "日";
        }
        if (hourVisible) {
            time += nowHour + "时";
        }
        if (minuteVisible) {
            time += nowMinute + "分";
        }
        if (secondVisible) {
            time += nowSecond + "秒";
        }
        return time;
    }

    public String getFormat() {
        String format = "";
        if (yearVisible) {
            format += "yyyy年";
        }
        if (monthVisible) {
            format += "MM月";
        }
        if (dayVisible) {
            format += "dd日";
        }
        if (hourVisible) {
            format += "HH时";
        }
        if (minuteVisible) {
            format += "mm分";
        }
        if (secondVisible) {
            format += "ss秒";
        }
        return format;
    }

    private ArrayList<String> years;
    private ArrayList<String> months;
    private ArrayList<String> days;
    private ArrayList<String> hours;
    private ArrayList<String> minutes;
    private ArrayList<String> seconds;

    private int startYear = 1970;
    private int endYear = 2100;
    private int selectedYear = 2010;
    private int startMonth = 1;
    private int endMonth = 12;
    private int selectedMonth = 1;
    private int startDay = 1;
    private int endDay = 31;
    private int selectedDay = 1;
    private int startHour = 0;
    private int endHour = 23;
    private int selectedHour = 0;
    private int startMinute = 0;
    private int endMinute = 59;
    private int selectedMinute = 0;
    private int startSecond = 0;
    private int endSecond = 59;
    private int selectedSecond = 0;

    public void setStartTime(int year, int month, int day, int hour, int minute, int second) {
        startYear = year;
        startMonth = month;
        startDay = day;
        startHour = hour;
        startMinute = minute;
        startSecond = second;
    }

    public void setStartTime(long timeMS) {
        startYear = Integer.valueOf(CommonUtil.formatDate(timeMS, "yyyy"));
        startMonth = Integer.valueOf(CommonUtil.formatDate(timeMS, "M"));
        startDay = Integer.valueOf(CommonUtil.formatDate(timeMS, "d"));
        startHour = Integer.valueOf(CommonUtil.formatDate(timeMS, "H"));
        startMinute = Integer.valueOf(CommonUtil.formatDate(timeMS, "m"));
        startSecond = Integer.valueOf(CommonUtil.formatDate(timeMS, "s"));
    }

    public void setEndTime(int year, int month, int day, int hour, int minute, int second) {
        endYear = year;
        endMonth = month;
        endDay = day;
        endHour = hour;
        endMinute = minute;
        endSecond = second;
    }

    public void setEndTime(long timeMS) {
        endYear = Integer.valueOf(CommonUtil.formatDate(timeMS, "yyyy"));
        endMonth = Integer.valueOf(CommonUtil.formatDate(timeMS, "M"));
        endDay = Integer.valueOf(CommonUtil.formatDate(timeMS, "d"));
        endHour = Integer.valueOf(CommonUtil.formatDate(timeMS, "H"));
        endMinute = Integer.valueOf(CommonUtil.formatDate(timeMS, "m"));
        endSecond = Integer.valueOf(CommonUtil.formatDate(timeMS, "s"));
    }

    public void setSelectedTime(int year, int month, int day, int hour, int minute, int second) {
        selectedYear = year;
        selectedMonth = month;
        selectedDay = day;
        selectedHour = hour;
        selectedMinute = minute;
        selectedSecond = second;
    }

    public void setSelectedTime(long timeMS) {
        selectedYear = Integer.valueOf(CommonUtil.formatDate(timeMS, "yyyy"));
        selectedMonth = Integer.valueOf(CommonUtil.formatDate(timeMS, "M"));
        selectedDay = Integer.valueOf(CommonUtil.formatDate(timeMS, "d"));
        selectedHour = Integer.valueOf(CommonUtil.formatDate(timeMS, "H"));
        selectedMinute = Integer.valueOf(CommonUtil.formatDate(timeMS, "m"));
        selectedSecond = Integer.valueOf(CommonUtil.formatDate(timeMS, "s"));
    }

    private int visibleNum = 5;

    /**
     * 显示的选项数量
     *
     * @param num
     */
    public void setVisibleNum(int num) {
        visibleNum = num;
    }

    private boolean wheelLoop = false;

    /**
     * 循环滚动
     *
     * @param loop
     */
    public void setLoop(boolean loop) {
        wheelLoop = loop;
    }

    private WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
    private WheelView.Skin skin = WheelView.Skin.None;

    public void setWheelStyle(WheelView.WheelViewStyle style) {
        this.style = style;
    }

    public void setWheelSkin(WheelView.Skin skin) {
        this.skin = skin;
    }
}
