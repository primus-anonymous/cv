package com.neocaptainnemo.cv.ui.projects.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.text.parseAsHtml
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.compose.*
import com.neocaptainnemo.cv.ui.projects.ProjectDetailsViewModel

private val IMG_HEIGHT = 220.dp
private val LOGO_WIDTH = 128.dp

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

    MaterialTheme(colors = CvColors) {

        Box {

            ScrollableColumn {

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                    val (coverPic, projectPic, projTitle, vendor, back, share) = createRefs()

                    Box(modifier = Modifier.constrainAs(coverPic) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        height = Dimension.value(IMG_HEIGHT)
                        width = Dimension.fillToConstraints
                    }) {
                        UrlImage(url = coverImage.value.orEmpty(),
                                 modifier = Modifier.fillMaxSize()
                        )
                    }

                    Text(text = title.value,
                         style = TextStyle.Primary20,
                         textAlign = TextAlign.Justify,
                         modifier = Modifier
                                 .padding(DEFAULT_MARGIN)
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
                         style = TextStyle.Primary16,
                         modifier = Modifier
                                 .padding(end = DEFAULT_MARGIN,
                                          top = SMALL_MARGIN,
                                          bottom = HALF_MARGIN)
                                 .constrainAs(vendor)
                                 {
                                     end.linkTo(parent.end)
                                     top.linkTo(projectPic.bottom)
                                     centerHorizontallyTo(projectPic)
                                 })


                    Surface(shape = CircleShape,
                            elevation = 2.dp,
                            modifier = Modifier
                                    .padding(end = DEFAULT_MARGIN)
                                    .zIndex(1.0f)
                                    .constrainAs(projectPic) {
                                        width = Dimension.value(LOGO_WIDTH)
                                        end.linkTo(parent.end)
                                        centerAround(coverPic.bottom)
                                    }
                    ) {
                        UrlImage(url = projectImage.value.orEmpty(),
                                 modifier = Modifier
                                         .fillMaxSize()
                        )
                    }

                    Image(imageVector = vectorResource(id = R.drawable.ic_back),
                          modifier = Modifier
                                  .constrainAs(back) {
                                      top.linkTo(parent.top)
                                      start.linkTo(parent.start)
                                  }
                                  .zIndex(1.0f)
                                  .clickable(onClick = {
                                      backClicked?.invoke()
                                  })
                                  .padding(DEFAULT_MARGIN)
                    )

                    Image(imageVector = vectorResource(id = R.drawable.ic_baseline_share_24),
                          modifier = Modifier
                                  .constrainAs(share) {
                                      top.linkTo(parent.top)
                                      end.linkTo(parent.end)
                                  }
                                  .zIndex(1.0f)
                                  .clickable(onClick = {
                                      shareClicked?.invoke()
                                  })
                                  .padding(16.dp)
                    )
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
                         modifier = Modifier.padding(horizontal = DEFAULT_MARGIN,
                                                     vertical = SMALL_MARGIN)
                                 .clickable(onClick = {
                                     sourceCodeCLicked?.invoke(viewModel.gitHubUrl.orEmpty())
                                 })
                    )
                }

                Column(Modifier.height(DEFAULT_MARGIN)) {}
            }

            if (storeVisibility.value == true) {
                FloatingActionButton(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(DEFAULT_MARGIN),
                                     content = {
                                         Image(vectorResource(id = R.drawable.ic_shop_white_24px))
                                     },
                                     onClick = {
                                         shopCLicked?.invoke(viewModel.storeUrl.orEmpty())
                                     })
            }
        }
    }
}


