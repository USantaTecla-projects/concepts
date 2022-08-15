/** Example file/folder data. */
export const files = [
  {
    name: 'Software',
    type: 'folder',
    children: [
      {
        name: 'src',
        type: 'folder',
        children: [
          {
            name: 'cdk',
            type: 'folder',
            children: [
              { name: 'package.json', type: 'file' },
              { name: 'BUILD.bazel', type: 'file' },
            ],
          },
          { name: 'material', type: 'folder' },
        ],
      },
    ],
  },
  {
    name: 'Hardware',
    type: 'folder',
    children: [
      {
        name: 'packages',
        type: 'folder',
        children: [
          { name: '.travis.yml', type: 'file' },
          { name: 'firebase.json', type: 'file' },
        ],
      },
      { name: 'package.json', type: 'file' },
    ],
  },
  {
    name: 'Haskell',
    type: 'folder',
    children: [
      { name: 'gulpfile.js', type: 'file' },
      { name: 'README.md', type: 'file' },
    ],
  },
];
