package main

import org.junit.Assert.assertEquals
import org.junit.Test

class MainTest {
    private val delta = 0.01

    // --- Тесты на превышение лимитов ---

    @Test
    fun `card daily limit exceeded returns -1`() {
        assertEquals(-1.0, commission(160_000.0, "Visa"), delta)
    }

    @Test
    fun `card monthly limit exceeded returns -1`() {
        assertEquals(-1.0, commission(50_000.0, "Visa", 560_000.0), delta)
    }

    @Test
    fun `vk pay per operation limit exceeded returns -1`() {
        assertEquals(-1.0, commission(16_000.0, "VK Play"), delta)
    }

    @Test
    fun `vk pay monthly limit exceeded returns -1`() {
        assertEquals(-1.0, commission(10_000.0, "VK Play", 35_000.0), delta)
    }

    // --- Тест на неизвестный тип карты (закрывает красные ветки else) ---

    @Test
    fun `unknown card type returns -1`() {
        assertEquals(-1.0, commission(100.0, "Unknown"), delta)
    }

    // --- Тесты для Mastercard / Maestro ---

    @Test
    fun `mastercard total in free range returns 0`() {
        assertEquals(0.0, commission(10_000.0, "Mastercard", 20_000.0), delta)
    }

    @Test
    fun `mastercard total below 300 returns commission`() {
        assertEquals(21.2, commission(200.0, "Maestro"), delta)
    }

    @Test
    fun `mastercard total above 75000 returns commission`() {
        assertEquals(500.0, commission(80_000.0, "Mastercard"), delta)
    }

    // --- Тесты для Visa / Мир ---

    @Test
    fun `visa commission is 0_75 percent when above minimum`() {
        assertEquals(75.0, commission(10_000.0, "Visa"), delta)
    }

    @Test
    fun `visa commission is minimum 35 when below`() {
        assertEquals(35.0, commission(1_000.0, "Visa"), delta)
    }

    @Test
    fun `mir commission same as visa`() {
        assertEquals(75.0, commission(10_000.0, "Мир"), delta)
    }

    // --- Тест для VK Play (успешный сценарий) ---

    @Test
    fun `vk pay returns 0 within limits`() {
        assertEquals(0.0, commission(10_000.0, "VK Play"), delta)
    }

    @Test
    fun `test empty card type`() {
        assertEquals(-1.0, commission(100.0, ""), delta)
    }

    @Test
    fun `test null-like edge case`() {
        assertEquals(-1.0, commission(15_001.0, "VK Play"), delta)
    }
    @Test
    fun `trick jacoco to reach second else`() {
        assertEquals(-1.0, commission(100.0, "Mastercard "), delta)
    }
}