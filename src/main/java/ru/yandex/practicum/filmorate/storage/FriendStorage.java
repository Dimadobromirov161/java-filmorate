public interface FriendStorage {
    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    List<User> findAllFriends(int id);

    List<User> findCommonFriends(int id, int otherId);
}
