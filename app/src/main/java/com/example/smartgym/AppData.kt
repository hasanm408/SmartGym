package com.example.smartgym

import com.example.smartgym.model.*
import java.text.SimpleDateFormat
import java.util.*

object AppData {
    val trainers = mutableListOf<Trainer>()
    val users = mutableListOf<User>()
    
    var currentUser: User? = null
    var currentTrainer: Trainer? = null
    var selectedTrainer: Trainer? = null
    var trainerPlan: TrainerPlan? = null

    val workoutLogs = mutableListOf<WorkoutLog>()
    val mealLogs = mutableListOf<MealLog>()
    val messages = mutableListOf<Message>()


    init {
        // Dummy Trainer
        val demoTrainer = Trainer(
            name = "John Doe",
            email = "trainer@gym.com",
            password = "123",
            age = 30,
            specialization = "Strength & Conditioning",
            experience = "8",
            time = "07:00 AM",
            availableDays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"),
            workoutPlan = mapOf(
                "Monday" to DayWorkout(mutableListOf(Exercise("Bench Press", 3, 12), Exercise("Cable Fly", 3, 15))),
                "Tuesday" to DayWorkout(mutableListOf(Exercise("Squats", 4, 10), Exercise("Leg Press", 3, 12)))
            ),
            dietPlan = mapOf(
                "Monday" to DayDiet("Oats & Protein", "Chicken & Rice", "Salmon & Veggies")
            )
        )
        trainers.add(demoTrainer)

        // Dummy User
        val demoUser = User("Alex Smith", "user@gym.com", "123", 25, "Male")
        users.add(demoUser)
    }

    fun getTodayDateKey(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun getTodayDay(): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
    }

    fun getWorkoutLog(userName: String, date: String): WorkoutLog {
        var log = workoutLogs.find { it.userName == userName && it.date == date }
        if (log == null) {
            log = WorkoutLog(userName, date)
            workoutLogs.add(log)
        }
        return log
    }

    fun getMealLog(userName: String, date: String): MealLog {
        var log = mealLogs.find { it.userName == userName && it.date == date }
        if (log == null) {
            log = MealLog(userName, date)
            mealLogs.add(log)
        }
        return log
    }

    fun getUserConsistency(userName: String): Int {
        val userLogs = workoutLogs.filter { it.userName == userName }
        if (userLogs.isEmpty()) return 0
        val completedCount = userLogs.count { it.isCompleted }
        return (completedCount * 100) / userLogs.size
    }

    fun getStreak(userName: String): Int {
        val sortedLogs = workoutLogs.filter { it.userName == userName && it.isCompleted }
            .sortedByDescending { it.date }
        if (sortedLogs.isEmpty()) return 0
        
        var streak = 0
        // Simple streak logic: consecutive days with completed workouts
        // For a prototype, we can just count total completed workouts as a "sessions streak"
        return sortedLogs.size 
    }

    fun getCurrentUserName(): String = currentUser?.name ?: "Guest"

    fun getMealStatus(userName: String, date: String): Triple<Boolean, Boolean, Boolean> {
        val log = getMealLog(userName, date)
        return Triple(log.breakfastDone, log.lunchDone, log.dinnerDone)
    }

    fun setMealStatus(
        userName: String,
        dateKey: String,
        breakfastDone: Boolean,
        lunchDone: Boolean,
        dinnerDone: Boolean
    ) {
        val log = getMealLog(userName, dateKey)
        log.breakfastDone = breakfastDone
        log.lunchDone = lunchDone
        log.dinnerDone = dinnerDone
    }

    fun getCompletedTaskStats(userName: String, date: String, exercises: List<Exercise>): Pair<Int, Int> {
        val workoutLog = getWorkoutLog(userName, date)
        val completedWorkouts = workoutLog.logs.count { it.completed }
        val mealLog = getMealLog(userName, date)
        var completedMeals = 0
        if (mealLog.breakfastDone) completedMeals++
        if (mealLog.lunchDone) completedMeals++
        if (mealLog.dinnerDone) completedMeals++
        
        return (completedWorkouts + completedMeals) to (exercises.size + 3)
    }

    fun updateStreak(userName: String, date: String, completed: Int, total: Int) {
        if (completed == total && total > 0) {
            val log = getWorkoutLog(userName, date)
            log.isCompleted = true
        }
    }

    fun ensureUserExists(name: String) {
        if (users.none { it.name == name }) {
            users.add(User(name = name, age = 20, gender = "Unknown"))
        }
    }

    fun getTrainerProgressList(): List<UserProgress> {
        val today = getTodayDateKey()
        return users.map { user ->
            val workoutLog = workoutLogs.find { it.userName == user.name && it.date == today }
            val completedExercises = workoutLog?.logs?.count { it.completed } ?: 0

            val mealLog = mealLogs.find { it.userName == user.name && it.date == today }
            var mealsDone = 0
            if (mealLog?.breakfastDone == true) mealsDone++
            if (mealLog?.lunchDone == true) mealsDone++
            if (mealLog?.dinnerDone == true) mealsDone++

            UserProgress(
                userName = user.name,
                completedExercises = completedExercises,
                completedMeals = mealsDone,
                streak = getStreak(user.name)
            )
        }
    }
}
