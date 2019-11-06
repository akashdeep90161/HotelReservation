package com.innovationm.hotel.helper.enums;

import com.innovationm.hotel.config.Constant;

public enum BookingStatus {

   None(Constant.NA), Confirm(Constant.CONFIRM_BOOKING),Processing(Constant.InProcess_BOOKING),Completed(Constant.COMPLEATED_BOOKING);

    private final char _status;

    BookingStatus(char _status) {
        this._status = _status;
    }

    public static BookingStatus get(char booking_status)
    {
        BookingStatus bs=None;
        switch (booking_status){
            case Constant.CONFIRM_BOOKING:
                bs=BookingStatus.Confirm;
                break;
             case Constant.InProcess_BOOKING:
                 bs=BookingStatus.Processing;
                 break;
             case Constant.COMPLEATED_BOOKING:
                 bs=BookingStatus.Completed;
                 break;
        }
        return bs;
    }
}
