class Range implements Iterable<Long> {

    private long fromInclusive;
    private long toExclusive;

    public Range(long from, long to) {
        this.fromInclusive = from;
        this.toExclusive = to;
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<>() {
            private final long length = toExclusive;
            private long current = fromInclusive;

            @Override
            public boolean hasNext() {
                return current < length;
            }

            @Override
            public Long next() {
                return current++;
            }
        };
    }
}