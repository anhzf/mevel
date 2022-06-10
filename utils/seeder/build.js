const isProd = process.argv.includes('--production');

require('esbuild').build({
  entryPoints: ['./src/index.js'],
  outdir: './dist',
  bundle: true,
  minify: isProd,
  platform: 'node',
  watch: !isProd,
  plugins: [
    // https://github.com/evanw/esbuild/issues/619#issuecomment-751995294
    {
      name: 'make-all-packages-external',
      setup(build) {
        let filter = /^[^.\/]|^\.[^.\/]|^\.\.[^\/]/ // Must not start with "/" or "./" or "../"
        build.onResolve({ filter }, args => ({ path: args.path, external: true }))
      },
    }
  ]
});
