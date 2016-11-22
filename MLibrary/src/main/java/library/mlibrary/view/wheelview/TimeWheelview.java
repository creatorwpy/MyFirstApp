package library.mlibrary.view.wheelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import library.mlibrary.util.common.CommonUtil;

/**
 * 时间滚轮控件
 * Created by Harmy on 2016/1/18 0018.
 */
@SuppressLint("NewApi")
public class TimeWheelview extends LinearLayout {
    public static final String TIME_FRMAT = "yyyy年MM月dd日HH时mm分ss秒";

    public TimeWheelview(Context context) {
        super(context);
        init();
    }

    public TimeWheelview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeWheelview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TimeWheelview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private LoopView yearView;
    private TextView yearText;
    private boolean yearVisible = true;
    private LoopView monthView;
    private TextView monthText;
    private boolean monthVisible = true;
    private LoopView dayView;
    private TextView dayText;
    private boolean dayVisible = true;
    private LoopView hourView;
    private TextView hourText;
    private boolean hourVisible = true;
    private LoopView minuteView;
    private TextView minuteText;
    private boolean minuteVisible = true;
    private LoopView secondView;
    private TextView secondText;
    private boolean secondVisible = true;

    private void init() {
        LayoutParams rootlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setOrientation(HORIZONTAL);
        rootlp.gravity = Gravity.CENTER;
        setLayoutParams(rootlp);
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        seconds = new ArrayList<>();
        addWheelViews();
    }


    private LayoutParams lp;

    private void addWheelViews() {
        removeAllViews();
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        if (yearVisible) {
            yearView = new LoopView(getContext());
            yearView.setLayoutParams(lp);
            yearView.setViewPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            yearView.setColor(colorLine, colorOuter, colorCenter);
            yearView.setIsLoop(wheelLoop);
            yearView.setSpeed(wheelSpeed);
            yearView.setTextSize(textSize);
            yearView.setVisibleNum(visibleNum);
            years.clear();
            for (int i = startYear; i <= endYear; i++) {
                years.add(String.valueOf(i));
            }
            int position = 0;
            for (String value : years) {
                if (Integer.valueOf(value) == selectedYear) {
                    break;
                }
                position++;
            }
            yearView.setInitPosition(position);
            yearView.setItems(years);
            nowYear = years.get(yearView.getSelectedItem());
            yearView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    nowYear = years.get(index);
                    onYearListener(false);
                }
            });
            addView(yearView);
            yearText = new TextView(getContext());
            yearText.setLayoutParams(lp);
            yearText.setText("年");
            yearText.setTextColor(colorText);
            yearText.setTextSize(textSize);
            addView(yearText);
        }
        if (monthVisible) {
            monthView = new LoopView(getContext());
            monthView.setLayoutParams(lp);
            monthView.setViewPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            monthView.setColor(colorLine, colorOuter, colorCenter);
            monthView.setIsLoop(wheelLoop);
            monthView.setSpeed(wheelSpeed);
            monthView.setTextSize(textSize);
            monthView.setVisibleNum(visibleNum);
//            for (int i = startMonth; i <= 12; i++) {
//                String value = String.valueOf(i);
//                if (value.length() == 1) {
//                    value = "0" + value;
//                }
//                months.add(value);
//            }
            onYearListener(true);
            int position = 0;
            for (String value : months) {
                if (Integer.valueOf(value) == selectedMonth) {
                    break;
                }
                position++;
            }
            monthView.setInitPosition(position);
            nowMonth = months.get(monthView.getSelectedItem());
            monthView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    nowMonth = months.get(index);
                    onMonthListener(false);
                }
            });
            addView(monthView);
            monthText = new TextView(getContext());
            monthText.setLayoutParams(lp);
            monthText.setText("月");
            monthText.setTextColor(colorText);
            monthText.setTextSize(textSize);
            addView(monthText);
        }
        if (dayVisible) {
            dayView = new LoopView(getContext());
            dayView.setLayoutParams(lp);
            dayView.setViewPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            dayView.setColor(colorLine, colorOuter, colorCenter);
            dayView.setIsLoop(wheelLoop);
            dayView.setSpeed(wheelSpeed);
            dayView.setTextSize(textSize);
            dayView.setVisibleNum(visibleNum);
//            for (int i = startDay; i <= endDay; i++) {
//                String value = String.valueOf(i);
//                if (value.length() == 1) {
//                    value = "0" + value;
//                }
//                days.add(value);
//            }
            onMonthListener(true);
            int position = 0;
            for (String value : days) {
                if (Integer.valueOf(value) == selectedDay) {
                    break;
                }
                position++;
            }
            dayView.setInitPosition(position);
            nowDay = days.get(dayView.getSelectedItem());
            dayView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    nowDay = days.get(index);
                    onDayListener(false);
                }
            });
            addView(dayView);
            dayText = new TextView(getContext());
            dayText.setLayoutParams(lp);
            dayText.setText("日");
            dayText.setTextColor(colorText);
            dayText.setTextSize(textSize);
            addView(dayText);
        }
        if (hourVisible) {
            hourView = new LoopView(getContext());
            hourView.setLayoutParams(lp);
            hourView.setViewPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            hourView.setColor(colorLine, colorOuter, colorCenter);
            hourView.setIsLoop(wheelLoop);
            hourView.setSpeed(wheelSpeed);
            hourView.setTextSize(textSize);
            hourView.setVisibleNum(visibleNum);
            hours = new ArrayList<>();
//            for (int i = startHour; i <= endHour; i++) {
//                String value = String.valueOf(i);
//                if (value.length() == 1) {
//                    value = "0" + value;
//                }
//                hours.add(value);
//            }
            onDayListener(true);
            int position = 0;
            for (String value : hours) {
                if (Integer.valueOf(value) == selectedHour) {
                    break;
                }
                position++;
            }
            hourView.setInitPosition(position);
            nowHour = hours.get(hourView.getSelectedItem());
            hourView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    nowHour = hours.get(index);
                    onHourListener(false);
                }
            });
            addView(hourView);
            hourText = new TextView(getContext());
            hourText.setLayoutParams(lp);
            hourText.setText("时");
            hourText.setTextColor(colorText);
            hourText.setTextSize(textSize);
            addView(hourText);
        }
        if (minuteVisible) {
            minuteView = new LoopView(getContext());
            minuteView.setLayoutParams(lp);
            minuteView.setViewPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            minuteView.setColor(colorLine, colorOuter, colorCenter);
            minuteView.setIsLoop(wheelLoop);
            minuteView.setSpeed(wheelSpeed);
            minuteView.setTextSize(textSize);
            minuteView.setVisibleNum(visibleNum);
            minutes = new ArrayList<>();
//            for (int i = startMinute; i <= endMinute; i++) {
//                String value = String.valueOf(i);
//                if (value.length() == 1) {
//                    value = "0" + value;
//                }
//                minutes.add(value);
//            }
            onHourListener(true);
            int position = 0;
            for (String value : minutes) {
                if (Integer.valueOf(value) == selectedMinute) {
                    break;
                }
                position++;
            }
            minuteView.setInitPosition(position);
            nowMinute = minutes.get(minuteView.getSelectedItem());
            minuteView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    nowMinute = minutes.get(index);
                    onMinuteListener(false);
                }
            });
            addView(minuteView);
            minuteText = new TextView(getContext());
            minuteText.setLayoutParams(lp);
            minuteText.setText("分");
            minuteText.setTextColor(colorText);
            minuteText.setTextSize(textSize);
            addView(minuteText);
        }
        if (secondVisible) {
            secondView = new LoopView(getContext());
            secondView.setLayoutParams(lp);
            secondView.setViewPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            secondView.setColor(colorLine, colorOuter, colorCenter);
            secondView.setIsLoop(wheelLoop);
            secondView.setSpeed(wheelSpeed);
            secondView.setTextSize(textSize);
            secondView.setVisibleNum(visibleNum);
            seconds = new ArrayList<>();
//            for (int i = startSecond; i <= endSecond; i++) {
//                String value = String.valueOf(i);
//                if (value.length() == 1) {
//                    value = "0" + value;
//                }
//                seconds.add(value);
//            }
            onMinuteListener(true);
            int position = 0;
            for (String value : seconds) {
                if (Integer.valueOf(value) == selectedSecond) {
                    break;
                }
                position++;
            }
            secondView.setInitPosition(position);
            nowSecond = seconds.get(secondView.getSelectedItem());
            secondView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    nowSecond = seconds.get(index);
                    onSecondListener(false);
                }
            });
            addView(secondView);
            secondText = new TextView(getContext());
            secondText.setLayoutParams(lp);
            secondText.setText("秒");
            secondText.setTextColor(colorText);
            secondText.setTextSize(textSize);
            addView(secondText);
        }
    }

    private void onYearListener(boolean init) {
        int index = 0;
        if (yearView.getSelectedItem() > years.size() - 1) {
            index = years.size() - 1;
        } else {
            index = yearView.getSelectedItem();
        }
        nowYear = years.get(index);
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
            monthView.setItems(months);
            if (!init) {
                updateViewLayout(monthView, lp);
            }
        }
        if (!init) {
            onMonthListener(false);
        }
    }

    private void onMonthListener(boolean init) {
        int index = 0;
        if (monthView.getSelectedItem() > months.size() - 1) {
            index = months.size() - 1;
        } else {
            index = monthView.getSelectedItem();
        }
        nowMonth = months.get(index);
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
            dayView.setItems(days);
            if (!init) {
                updateViewLayout(dayView, lp);
            }
        }
        if (!init) {
            onDayListener(false);
        }
    }

    private void onDayListener(boolean init) {
        int index = 0;
        if (dayView.getSelectedItem() > days.size() - 1) {
            index = days.size() - 1;
        } else {
            index = dayView.getSelectedItem();
        }
        nowDay = days.get(index);
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
            hourView.setItems(hours);
            if (!init) {
                updateViewLayout(hourView, lp);
            }
        }
        if (!init) {
            onHourListener(false);
        }
    }

    private void onHourListener(boolean init) {
        int index = 0;
        if (hourView.getSelectedItem() > hours.size() - 1) {
            index = hours.size() - 1;
        } else {
            index = hourView.getSelectedItem();
        }
        nowHour = hours.get(index);
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
            minuteView.setItems(minutes);
            if (!init) {
                updateViewLayout(minuteView, lp);
            }
        }
        if (!init) {
            onMinuteListener(false);
        }
    }

    private void onMinuteListener(boolean init) {
        int index = 0;
        if (minuteView.getSelectedItem() > minutes.size() - 1) {
            index = minutes.size() - 1;
        } else {
            index = minuteView.getSelectedItem();
        }
        nowMinute = minutes.get(index);
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
            secondView.setItems(seconds);
            if (!init) {
                updateViewLayout(secondView, lp);
            }
        }
        if (!init) {
            onSecondListener(false);
        }
    }

    private void onSecondListener(boolean init) {
        int index = 0;
        if (secondView.getSelectedItem() > seconds.size() - 1) {
            index = seconds.size() - 1;
        } else {
            index = secondView.getSelectedItem();
        }
        nowSecond = seconds.get(index);
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
    public void setEndTimeLong(long timeMS) {
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

    private int colorLine = 0xffc5c5c5;
    private int colorOuter = 0xffafafaf;
    private int colorCenter = 0xff313131;
    private int colorText = 0xff313131;

    /**
     * @param line   中间线颜色
     * @param outer  中间线以外的选项颜色
     * @param center 中间选中项颜色
     * @param text   年月日时分秒的颜色
     */
    public void setColors(int line, int outer, int center, int text) {
        colorLine = line;
        colorOuter = outer;
        colorCenter = center;
        colorText = text;
    }

    private int paddingLeft = 5;
    private int paddingRight = 5;
    private int paddingTop = 5;
    private int paddingBottom = 5;

    /**
     * 上下左右间距
     *
     * @param x
     * @param y
     */
    public void setViewPadding(int x, int y) {
        paddingLeft = x;
        paddingRight = x;
        paddingTop = y;
        paddingBottom = y;
    }

    private int visibleNum = 7;

    /**
     * 显示的选项数量
     *
     * @param num
     */
    public void setVisibleNum(int num) {
        visibleNum = num;

    }

    private int wheelSpeed = 10;

    /**
     * 滚轮速度，值越小速度越快
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        wheelSpeed = speed;
    }

    private float textSize = 15;

    public void setTextSize(float size) {
        textSize = size;
    }

    private boolean wheelLoop = true;

    /**
     * 循环滚动
     *
     * @param loop
     */
    public void setLoop(boolean loop) {
        wheelLoop = loop;
    }
}
