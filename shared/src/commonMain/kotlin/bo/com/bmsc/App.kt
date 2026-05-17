package bo.com.bmsc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bo.com.bmsc.app.theme.BMSCTheme
import bo.com.bmsc.auth.presentation.screen.LoginScreen
import bo.com.bmsc.core.extension.replace
import bo.com.bmsc.core.extension.safePopBackStack
import bo.com.bmsc.core.navigation.NavigationEvent
import bo.com.bmsc.core.navigation.NavigationHelper
import bo.com.bmsc.gamification.presentation.screen.GamificationScreen
import bo.com.bmsc.home.presentation.screen.HomeScreen
import bo.com.bmsc.racha.presentation.screen.ConfirmRachaScreen
import bo.com.bmsc.racha.presentation.screen.ConfigureSeasonScreen
import bo.com.bmsc.racha.presentation.screen.InvitePeopleScreen
import bo.com.bmsc.racha.presentation.screen.RachaOnboardingScreen
import bo.com.bmsc.streak.presentation.screen.CreateStreakScreen
import org.koin.compose.koinInject

@Composable
fun App() {
  val navController = rememberNavController()
  val navigationHelper: NavigationHelper = koinInject()

  BMSCTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
    ) {
      AppNavigation(
        navController = navController,
        navigationHelper = navigationHelper,
      )
    }
  }
}

@Composable
private fun AppNavigation(
  navController: NavHostController,
  navigationHelper: NavigationHelper,
) {
  val navigationEvent by navigationHelper.navigationEvents.collectAsStateWithLifecycle(
    initialValue = null,
  )

  LaunchedEffect(navigationEvent) {
    when (val event = navigationEvent) {
      is NavigationEvent.NavigateTo -> {
        navController.safeNavigate(event.route)
      }
      NavigationEvent.NavigateBack -> {
        navController.safePopBackStack()
      }
      is NavigationEvent.Replace -> {
        navController.replace(event.route)
      }
      null -> Unit
    }
  }

  NavHost(
    navController = navController,
    startDestination = Route.Login,
  ) {
    composable<Route.Login> {
      LoginScreen(
        onNavigateToHome = {
          navController.replace(Route.Home)
        },
        onNavigateToRegister = { /* TODO: Register screen */ },
        onNavigateToForgotPassword = { /* TODO: Forgot password screen */ },
        onNavigateToFaceId = { /* TODO: Face ID screen */ },
      )
    }

    composable<Route.Home> {
      HomeScreen(
        onMenuClick = { /* Handle menu click */ },
        onNavItemClick = { /* Handle nav item click */ },
      )
    }

    composable<Route.Gamification> {
      GamificationScreen(
        onBackClick = { navigationHelper.navigateBack() },
      )
    }

    composable<Route.CreateStreak> {
      CreateStreakScreen(
        onNavigateBack = { navController.safePopBackStack() },
        onNavigateToNextStep = { /* TODO: Step 2 */ },
      )
    }

    // Racha (Mi Chanchito) Flow
    composable<Route.RachaOnboarding> {
      RachaOnboardingScreen(
        onContinue = { navController.safeNavigate(Route.RachaInvitePeople) },
      )
    }

    composable<Route.RachaInvitePeople> {
      InvitePeopleScreen(
        onBack = { navController.safePopBackStack() },
        onContinue = { navController.safeNavigate(Route.RachaConfigureSeason) },
      )
    }

    composable<Route.RachaConfigureSeason> {
      ConfigureSeasonScreen(
        onBack = { navController.safePopBackStack() },
        onContinue = { navController.safeNavigate(Route.RachaConfirm) },
      )
    }

    composable<Route.RachaConfirm> {
      ConfirmRachaScreen(
        onBack = { navController.safePopBackStack() },
        onConfirm = {
          // TODO: Show success and navigate to home
          navController.replace(Route.Home)
        },
      )
    }

    // Add more routes here as needed
  }
}

private fun NavHostController.safeNavigate(route: Route) {
  navigate(route) {
    launchSingleTop = true
  }
}