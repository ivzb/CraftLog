package com.ivzb.craftlog.data.mapper

import com.ivzb.craftlog.domain.model.Link
import com.ivzb.craftlog.data.entity.LinkEntity

fun LinkEntity.toLink(): Link {
    return Link(
        url = url,
        site = site,
        title = title,
        imageUrl = imageUrl,
    )
}

fun Link.toLinkEntity(): LinkEntity {
    return LinkEntity(
        url = url,
        site = site,
        title = title,
        imageUrl = imageUrl,
    )
}
