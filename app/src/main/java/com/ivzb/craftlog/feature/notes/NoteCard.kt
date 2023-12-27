package com.ivzb.craftlog.feature.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.util.appendLink
import com.ivzb.craftlog.util.onLinkClick
import java.util.Date

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    navigateToNoteDetail: (Note) -> Unit
) {
    if (note.link != null) {
        NoteLinkCard(modifier, note, navigateToNoteDetail)
    } else {
        NoteTextCard(modifier, note, navigateToNoteDetail)
    }
}

@Composable
private fun NoteTextCard(
    modifier: Modifier = Modifier,
    note: Note,
    navigateToNoteDetail: (Note) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navigateToNoteDetail(note)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp, 0.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = note.content,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
private fun NoteLinkCard(
    modifier: Modifier = Modifier,
    note: Note,
    navigateToNoteDetail: (Note) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navigateToNoteDetail(note)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
                .defaultMinSize(minHeight = 130.dp)
        ) {


            val (image, title, url, arrow) = createRefs()
            val guideline = createGuidelineFromStart(0.3f)

            AsyncImage(
                modifier = Modifier.constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(guideline)
                }.fillMaxSize(),
                model = note.link?.imageUrl,
                contentDescription = "Note image preview",
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier
                    .padding(top = 16.dp, end = 8.dp, bottom = 4.dp, start = 16.dp)
                    .constrainAs(title) {
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                        top.linkTo(parent.top)
                        start.linkTo(guideline)
                        end.linkTo(arrow.start)
                        bottom.linkTo(url.top)
                    },
                text = note.link?.title ?: "",
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis
            )

            val annotatedLink = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xff64B5F6), fontWeight = FontWeight.Bold)) {
                    val link = note.link?.url ?: ""
                    appendLink(link, link)
                }
            }

            ClickableText(
                modifier = Modifier
                    .padding(top = 4.dp, end = 8.dp, bottom = 16.dp, start = 16.dp)
                    .constrainAs(url) {
                        width = Dimension.fillToConstraints
                        start.linkTo(guideline)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(arrow.start)
                    },
                text = annotatedLink,
                onClick = { offset ->
                    annotatedLink.onLinkClick(offset) { link ->
                        try {
                            uriHandler.openUri(link)
                        } catch (_: Exception) {
                            // ignore
                        }
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                modifier = Modifier.constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun NoteCardPreview() {
    NoteCard(
        Modifier,
        Note(
            id = 123L,
            content = "dinner",
            tags = listOf(),
            date = Date(),
            link = null
        )
    ) { }
}
