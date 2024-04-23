package com.ispan.dogland;

import com.ispan.dogland.model.dao.RoomReservationRepository;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.service.interfaceFile.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class RoomReservationTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomReservationRepository roomReservationRepository;



    @Test
    @DisplayName("多執行緒下訂房測試")
    public void testConcurrentRoomReservation() throws InterruptedException {
        final int threads = 10; // 設定執行緒數量
        final int roomId = 1;   // 設定房間ID

        CountDownLatch latch = new CountDownLatch(threads);
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.execute(() -> {
                try {
                    // 每個執行緒進行訂房操作
                    RoomReservation roomReservation = new RoomReservation();
                    // 設定roomReservation的其他屬性
                    Integer reservationId = roomService.addRoomReservation(roomReservation, roomId, 1, 1);
                    // 可以根據返回的reservationId來判斷訂房是否成功
                } finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有執行緒結束
        latch.await();
        executor.shutdown();

        // 驗證訂房結果
        // 可以使用相應的斷言來驗證訂房結果
    }
}

