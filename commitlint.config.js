/**
 * @file commitlint.config.js
 * @description commitlint 설정 파일입니다.
 * '풀 바이브 코딩 프로젝트'의 커밋 메시지 컨벤션을 강제합니다.
 *
 * ## WIP(Work In Progress) 커밋에 대한 안내
 * 'WIP:' 접두사는 아직 작업이 완료되지 않은 임시 커밋을 위한 표시입니다.
 * 이 커밋들은 Pull Request를 Squash and Merge 하기 전에 반드시
 * rebase를 통해 하나의 의미 있는 커밋으로 합쳐져야 합니다.
 *
 * 따라서, commitlint 규칙 자체에는 'WIP'를 포함하지 않습니다.
 * 이는 최종적으로 main 브랜치에 남는 커밋의 품질을 보장하기 위함입니다.
 */

/** @type {import('cz-git').UserConfig} */
module.exports = {
  // @commitlint/config-conventional 규약을 기본으로 사용합니다.
  extends: ['@commitlint/config-conventional'],

  // 우리가 정의한 규칙으로 기본값을 재정의합니다.
  rules: {
    /**
     * type은 항상 소문자로 작성되어야 합니다.
     * [level, applicability, value]
     * level: 0(무시), 1(경고), 2(오류)
     * applicability: 'always' 또는 'never'
     * value: 규칙에 대한 값
     */
    'type-case': [2, 'always', 'lower-case'],

    // type은 비어있을 수 없습니다.
    'type-empty': [2, 'always', 'never'],

    // 우리가 정의한 type 목록 + revert만 허용합니다.
    'type-enum': [
      2,
      'always',
      [
        'feat',
        'fix',
        'docs',
        'style',
        'refactor',
        'test',
        'chore',
        'ci',
        'perf',
        'revert', // 되돌리기 커밋 타입 추가
      ],
    ],

    // scope는 항상 소문자로 작성되어야 합니다.
    'scope-case': [2, 'always', 'lower-case'],

    // subject는 비어있을 수 없습니다.
    'subject-empty': [2, 'always', 'never'],

    // subject는 마침표(.)로 끝나면 안 됩니다.
    'subject-full-stop': [2, 'never', '.'],

    // subject는 문장 형식으로 시작하면 안 됩니다. (동사 원형 규칙 강제)
    'subject-case': [
      2,
      'never',
      ['sentence-case', 'start-case', 'pascal-case', 'upper-case'],
    ],

    // body는 헤더와 한 줄의 공백으로 구분되어야 합니다.
    'body-leading-blank': [1, 'always'],

    // footer는 본문과 한 줄의 공백으로 구분되어야 합니다.
    'footer-leading-blank': [1, 'always'],

    // 헤더의 최대 길이는 100자입니다.
    'header-max-length': [2, 'always', 100],
  },
};
