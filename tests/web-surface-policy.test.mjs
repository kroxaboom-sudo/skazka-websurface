import assert from 'node:assert/strict';
import fs from 'node:fs';

const source = fs.readFileSync(
    'websurface/src/main/java/com/kroxaboom/skazka/websurface/SkazkaWebSurface.java',
    'utf8'
);

assert.match(source, /public final class SkazkaWebSurface/);
assert.match(source, /Context context/);
assert.doesNotMatch(source, /MainActivity/);
assert.match(source, /CustomTabsService/);
assert.match(source, /Web target must use HTTPS/);
assert.match(source, /No Custom Tabs provider available/);
assert.doesNotMatch(source, /android\.webkit\.WebView|new\s+WebView\s*\(/);

console.log('PASS: Skazka WebSurface keeps the reusable HTTPS/Custom Tabs boundary');
