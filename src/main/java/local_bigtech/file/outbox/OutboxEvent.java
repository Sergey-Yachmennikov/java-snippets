package local_bigtech.file.outbox;

record OutboxEvent(long id, long fileId, String oldName, String newName, int attempts) {
}
