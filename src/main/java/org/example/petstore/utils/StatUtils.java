package org.example.petstore.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class providing helper methods for calculating
 * time-based ranges and percentage changes, commonly used in analytics and reporting.
 */
@Component
public class StatUtils {

    /**
     * Returns the start of the given day as a {@link LocalDateTime}.
     *
     * @param date the local date
     * @return {@link LocalDateTime} at 00:00 of the given date
     */
    public LocalDateTime getStartOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    /**
     * Returns the exclusive end of the given day (i.e. the start of the next day at 00:00).
     *
     * @param date the local date
     * @return {@link LocalDateTime} at 00:00 of the next day
     */
    public LocalDateTime getEndOfDayExclusive(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    /**
     * Creates a {@link DateRange} for a specific day from its start to the start of the next day.
     *
     * @param date the day to generate the range for
     * @return a {@link DateRange} from the start of the given day to the exclusive end
     */
    public DateRange getDayRange(LocalDate date) {
        return new DateRange(getStartOfDay(date), getEndOfDayExclusive(date));
    }

    /**
     * Generates a {@link ComparisonRange} representing the date ranges
     * for this week and the same weekday range from the previous week.
     *
     * @param today the current date
     * @return a {@link ComparisonRange} containing this week's and last week's ranges
     */
    public ComparisonRange getSameWeekdayRevenueRange(LocalDate today) {
        LocalDate startOfThisWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfThisWeek = today.plusDays(1); // exclusive end: tomorrow

        LocalDate sameDayLastWeek = today.minusWeeks(1);
        LocalDate startOfLastWeek = sameDayLastWeek.with(DayOfWeek.MONDAY);
        LocalDate endOfLastWeek = sameDayLastWeek.plusDays(1); // exclusive end

        DateRange thisWeekRange = new DateRange(
                getStartOfDay(startOfThisWeek),
                getStartOfDay(endOfThisWeek)
        );

        DateRange lastWeekRange = new DateRange(
                getStartOfDay(startOfLastWeek),
                getStartOfDay(endOfLastWeek)
        );

        return new ComparisonRange(thisWeekRange, lastWeekRange);
    }

    /**
     * Calculates the percentage change between two long values.
     *
     * @param current  the current value
     * @param previous the previous value
     * @return percentage change as an integer; returns 0 if previous is 0
     */
    public int calculatePercentageChange(long current, long previous) {
        if (previous == 0) return 0;
        return (int) (((double) (current - previous) / previous) * 100);
    }

    /**
     * Calculates the percentage change between two {@link BigDecimal} values.
     *
     * @param current  the current value
     * @param previous the previous value
     * @return percentage change as an integer; returns 0 if previous is null or zero
     */
    public int calculatePercentageChange(BigDecimal current, BigDecimal previous) {
        if (previous == null || previous.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        return current.subtract(previous)
                .divide(previous, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .intValue();
    }

    /**
     * Represents a time range between two {@link LocalDateTime} values.
     *
     * @param start start of the range (inclusive)
     * @param end   end of the range (exclusive)
     */
    public record DateRange(LocalDateTime start, LocalDateTime end) {}

    /**
     * Represents two comparable {@link DateRange}s — typically this week vs last week.
     *
     * @param thisWeek the date range for the current week
     * @param lastWeek the date range for the previous week
     */
    public record ComparisonRange(DateRange thisWeek, DateRange lastWeek) {}
}
