package com.example.roomreservation.exception;

import com.example.roomreservation.exception.branch.BranchNotFoundException;
import com.example.roomreservation.exception.branch.NoSuchBranchException;
import com.example.roomreservation.exception.reservation.InvalidReservationTimeException;
import com.example.roomreservation.exception.reservation.ReservationNotFoundException;
import com.example.roomreservation.exception.room.RoomIsReservedException;
import com.example.roomreservation.exception.room.RoomNotFoundException;
import com.example.roomreservation.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(InvalidReservationTimeException.class)
    public ResponseEntity<String> handleInvalidReservationTimeException(InvalidReservationTimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

  @ExceptionHandler(RoomIsReservedException.class)
  public ResponseEntity<String> handleRoomIsReservedException(RoomIsReservedException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFoundException(RoomNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(NoSuchBranchException.class)
    public ResponseEntity<String> handleNoSuchBranchException(NoSuchBranchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<String> handleBranchNotFoundException(BranchNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
