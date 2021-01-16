package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.text.parseAsHtml
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.*
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsViewModel
import kotlin.math.min

private val imageHeight = 220.dp
private val logoWidth = 128.dp
private val appbarAppearenceThreshHold = 64.dp

@Composable
fun ProjectDetailsScreen(
        viewModel: ProjectDetailsViewModel,
        shopCLicked: ((url: String) -> Unit)?,
        sourceCodeCLicked: ((url: String) -> Unit)?,
        backClicked: (() -> Unit)?,
        shareClicked: (() -> Unit)?,
) {

    val title = viewModel.projectName.collectAsState("")
    val company = viewModel.company.collectAsState("")
    val coverImage = viewModel.coverPic.collectAsState(null)
    val projectImage = viewModel.webPic.collectAsState(null)
    val description = viewModel.detailsDescription.collectAsState(null)
    val duties = viewModel.duties.collectAsState(null)
    val stack = viewModel.stack.collectAsState(null)
    val sourceCode = viewModel.sourceCode.collectAsState(null)
    val storeVisibility = viewModel.storeVisibility.collectAsState(null)

    val scrollState = rememberScrollState(0.0f)
    val scrollHolder = ScrollsHolder()

    MaterialTheme(colors = cvColors()) {

        Box(modifier = Modifier.fillMaxHeight()) {
            ScrollableColumn(scrollState = scrollState) {

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                    val (coverPic, projectPic, projTitle, vendor) = createRefs()

                    Box(modifier = Modifier.constrainAs(coverPic) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        height = Dimension.value(imageHeight)
                        width = Dimension.fillToConstraints
                    }) {
                        UrlImage(url = coverImage.value.orEmpty(),
                                 modifier = Modifier.fillMaxSize()
                        )
                    }

                    Text(text = title.value,
                         style = TextStyle.primary20(),
                         textAlign = TextAlign.Justify,
                         modifier = Modifier
                                 .padding(defaultMargin)
                                 .constrainAs(projTitle)
                                 {
                                     start.linkTo(parent.start)
                                     top.linkTo(coverPic.bottom)
                                     end.linkTo(projectPic.start)
                                     bottom.linkTo(parent.bottom)
                                     width = Dimension.fillToConstraints
                                 }
                    )

                    Text(text = company.value,
                         style = TextStyle.primary16(),
                         modifier = Modifier
                                 .padding(end = defaultMargin,
                                          top = smallMargin,
                                          bottom = halfMargin)
                                 .constrainAs(vendor)
                                 {
                                     end.linkTo(parent.end)
                                     top.linkTo(projectPic.bottom)
                                     centerHorizontallyTo(projectPic)
                                 })


                    Surface(shape = CircleShape,
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
                        UrlImage(url = projectImage.value.orEmpty(),
                                 modifier = Modifier
                                         .fillMaxSize()
                        )
                    }
                }

                if (description.value.isNullOrBlank()
                                .not()) {
                    SectionDescription(string = description.value.orEmpty()
                            .parseAsHtml())
                }

                if (duties.value.isNullOrBlank()
                                .not()) {
                    SectionTitle(stingRes = R.string.duties)
                    SectionDescription(string = duties.value.orEmpty()
                            .parseAsHtml())
                }

                if (stack.value.isNullOrBlank()
                                .not()) {
                    SectionTitle(stingRes = R.string.stack)
                    SectionDescription(string = stack.value.orEmpty()
                            .parseAsHtml())
                }

                if (sourceCode.value.isNullOrBlank()
                                .not()) {
                    SectionTitle(stingRes = R.string.source_code)
                    Text(text = sourceCode.value.orEmpty(),
                         style = TextStyle.Link14,
                         modifier = Modifier.padding(horizontal = defaultMargin,
                                                     vertical = smallMargin)
                                 .clickable(onClick = {
                                     sourceCodeCLicked?.invoke(viewModel.gitHubUrl.orEmpty())
                                 })
                    )
                }

                Column(Modifier.height(defaultMargin)) {}
            }

            if (storeVisibility.value == true) {
                FloatingActionButton(modifier = Modifier
                        .translateOnScroll(scrollState, scrollHolder)
                        .align(Alignment.BottomEnd)
                        .padding(defaultMargin),
                                     content = {
                                         Image(vectorResource(id = R.drawable.ic_shop_white_24px))
                                     },
                                     onClick = {
                                         shopCLicked?.invoke(viewModel.storeUrl.orEmpty())
                                     })

            }


            TopAppBar(
                    title = {},
                    backgroundColor = MaterialTheme.colors.primarySurface
                            .copy(alpha = min(1.0f, scrollState.value /
                                    with(AmbientDensity.current) {
                                        appbarAppearenceThreshHold.toPx()
                                    }
                            )),
                    navigationIcon = {
                        Image(vectorResource(id = R.drawable.ic_back),
                              modifier = Modifier.padding(start = halfMargin)
                                      .clickable(onClick = {
                                          backClicked?.invoke()
                                      }))

                    },
                    actions = {
                        Image(vectorResource(id = R.drawable.ic_baseline_share_24),
                              modifier = Modifier.padding(end = halfMargin)
                                      .clickable(onClick = {
                                          shareClicked?.invoke()
                                      }))
                    },
                    elevation = 0.dp,
                    modifier = Modifier.zIndex(1.0f)
            )
        }
    }
}

