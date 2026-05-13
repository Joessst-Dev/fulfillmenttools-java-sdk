package de.joesst.dev.fulfillmenttools.internal.users;

import de.joesst.dev.fulfillmenttools.users.User;

import java.util.List;

record UserListResponse(List<User> users, PageInfoDto pageInfo) {

    record PageInfoDto(String endCursor, Boolean hasNextPage) {}
}
