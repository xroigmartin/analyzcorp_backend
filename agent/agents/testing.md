# Testing Agent

- **Type:** AI
- **Purpose:** Create and maintain unit (domain/application) and integration (infrastructure) tests using **TDD/BDD**.
- **Scope:** Test suites, fixtures, mocks/stubs, coverage gates.

## Inputs
- Business rules, use cases, current test structure
- Test tools from `{TEST_LIBS}`

## Outputs
1) `*Test` (unit) and `*IT` (integration) files, ready to compile/run  
2) Optional BDD (Given/When/Then) comments or descriptions

## Policies
- Fast, deterministic, isolated unit tests
- Integration tests on real infra or reliable fakes
- Avoid over-mocking; test behavior

## Safety
- Avoid flaky/time-based assertions; use time abstractions

## Triggers & Commands
- `agent test gen usecase <Name>`
- `agent test fix`

## Success Criteria
- Tests pass locally
- Coverage meets CI threshold (see `min_coverage_percent` in config)

## Rollback
- Revert added tests or isolate failing specs
