package com.neocaptainnemo.cv.ui.projects.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.parseAsHtml
import androidx.hilt.navigation.compose.hiltViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.analytics.AnalyticsEvent
import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.ui.compose.Link14
import com.neocaptainnemo.cv.ui.compose.ScrollsHolder
import com.neocaptainnemo.cv.ui.compose.UrlImage
import com.neocaptainnemo.cv.ui.compose.cvColors
import com.neocaptainnemo.cv.ui.compose.defaultMargin
import com.neocaptainnemo.cv.ui.compose.halfMargin
import com.neocaptainnemo.cv.ui.compose.primary16
import com.neocaptainnemo.cv.ui.compose.primary20
import com.neocaptainnemo.cv.ui.compose.smallMargin
import com.neocaptainnemo.cv.ui.compose.translateOnScroll
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsViewModel
import kotlin.math.min

private val imageHeight = 220.dp
private val logoWidth = 128.dp
private val appbarAppearanceThreshHold = 64.dp

@Composable
fun ProjectDetailsScreen(
    projectId: String,
    analyticsService: AnalyticsService,
    vm: ProjectDetailsViewModel = hiltViewModel(),
    backClicked: (() -> Unit)?,
) {

    val title by remember(vm) { vm.projectName }.collectAsState("")
    val company by remember(vm) { vm.company }.collectAsState("")
    val coverImage by remember(vm) { vm.coverPic }.collectAsState(null)
    val projectImage by remember(vm) { vm.webPic }.collectAsState(null)
    val description by remember(vm) { vm.detailsDescription }.collectAsState(null)
    val duties by remember(vm) { vm.duties }.collectAsState(null)
    val stack by remember(vm) { vm.stack }.collectAsState(null)
    val sourceCode by remember(vm) { vm.sourceCode }.collectAsState(null)
    val storeVisibility by remember(vm) { vm.storeVisibility }.collectAsState(null)
    val progress by remember(vm) { vm.progress }.collectAsState(false)
    val shareUrl by remember(vm) { vm.shareUrl }.collectAsState("")

    val scrollState = rememberScrollState()
    val scrollHolder = ScrollsHolder()

    val context = LocalContext.current

    LaunchedEffect(vm) {
        vm.fetchProject(projectId)
    }

    MaterialTheme(colors = cvColors()) {

        Box(modifier = Modifier.fillMaxHeight()) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                    val (coverPic, projectPic, projTitle, vendor) = createRefs()

                    Box(
                        modifier = Modifier.constrainAs(coverPic) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            height = Dimension.value(imageHeight)
                            width = Dimension.fillToConstraints
                        }
                    ) {
                        UrlImage(
                            url = coverImage.orEmpty(),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Text(
                        text = title,
                        style = TextStyle.primary20(),
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .padding(defaultMargin)
                            .constrainAs(projTitle) {
                                start.linkTo(parent.start)
                                top.linkTo(coverPic.bottom)
                                end.linkTo(projectPic.start)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints
                            }
                    )

                    Text(
                        text = company,
                        style = TextStyle.primary16(),
                        modifier = Modifier
                            .padding(
                                end = defaultMargin,
                                top = smallMargin,
                                bottom = halfMargin
                            )
                            .constrainAs(vendor) {
                                end.linkTo(parent.end)
                                top.linkTo(projectPic.bottom)
                                centerHorizontallyTo(projectPic)
                            }
                    )

                    Surface(
                        shape = CircleShape,
                        elevation = 2.dp,
                        modifier = Modifier
                            .padding(end = defaultMargin)
                            .zIndex(1.0f)
                            .constrainAs(projectPic) {
                                width = Dimension.value(logoWidth)
                                end.linkTo(parent.end)
                                centerAround(coverPic.bottom)
                            }
                    ) {
                        UrlImage(
                            url = projectImage.orEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                if (description.isNullOrBlank().not()
                ) {
                    SectionDescription(
                        string = description.orEmpty()
                            .parseAsHtml()
                    )
                }

                if (duties.isNullOrBlank().not()
                ) {
                    SectionTitle(stingRes = R.string.duties)
                    SectionDescription(
                        string = duties.orEmpty()
                            .parseAsHtml()
                    )
                }

                if (stack.isNullOrBlank().not()
                ) {
                    SectionTitle(stingRes = R.string.stack)
                    SectionDescription(
                        string = stack.orEmpty().parseAsHtml()
                    )
                }

                if (sourceCode.isNullOrBlank().not()
                ) {
                    SectionTitle(stingRes = R.string.source_code)
                    Text(
                        text = sourceCode.orEmpty(),
                        style = TextStyle.Link14,
                        modifier = Modifier
                            .padding(
                                horizontal = defaultMargin,
                                vertical = smallMargin
                            )
                            .clickable(onClick = {
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(vm.gitHubUrl.orEmpty())
                                ).also { intent ->
                                    context.startActivity(intent)
                                }

                                analyticsService.log(AnalyticsEvent.PROJECT_CODE_CLICKED)
                            })
                    )
                }

                Column(Modifier.height(defaultMargin)) {}
            }

            if (storeVisibility == true) {
                FloatingActionButton(
                    modifier = Modifier
                        .translateOnScroll(scrollState, scrollHolder)
                        .align(Alignment.BottomEnd)
                        .padding(defaultMargin),
                    content = {
                        Icon(painterResource(id = R.drawable.ic_store), contentDescription = null)
                    },
                    onClick = {
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(vm.storeUrl.orEmpty())
                        ).also { intent ->
                            context.startActivity(intent)
                        }
                        analyticsService.log(AnalyticsEvent.PROJECT_STORE_CLICKED)
                    }
                )
            }

            TopAppBar(
                title = {
                    if (scrollState.value > with(LocalDensity.current) {
                        appbarAppearanceThreshHold.toPx()
                    }
                    ) {
                        Text(text = title)
                    }
                },
                backgroundColor = MaterialTheme.colors.primarySurface
                    .copy(
                        alpha = min(
                            1.0f,
                            scrollState.value /
                                with(LocalDensity.current) {
                                    appbarAppearanceThreshHold.toPx()
                                }
                        )
                    ),
                navigationIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_back), contentDescription = null,
                        modifier = Modifier
                            .padding(start = halfMargin)
                            .clickable(onClick = {
                                backClicked?.invoke()
                            })
                    )
                },
                actions = {
                    Icon(
                        painterResource(id = R.drawable.ic_share), contentDescription = null,
                        modifier = Modifier
                            .padding(end = halfMargin)
                            .clickable(onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        shareUrl
                                    )
                                    type = "text/plain"
                                }

                                analyticsService.log(AnalyticsEvent.PROJECT_SHARED_CLICKED)

                                Intent
                                    .createChooser(
                                        sendIntent,
                                        null
                                    )
                                    .also {
                                        context.startActivity(it)
                                    }
                            })
                    )
                },
                elevation = 0.dp,
                modifier = Modifier.zIndex(1.0f)
            )
        }

        if (progress) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
