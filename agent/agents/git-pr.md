# Git & PR Agent

- **Type:** AI / Script
- **Purpose:** Create conventional commit messages and PR bodies with checklist, risks, and test plan.
- **Scope:** Commit messages, PR templates, labels.

## Inputs
- Diff/patch, issue context, scope

## Outputs
1) Commit messages (`feat:`, `fix:`, …)  
2) PR Markdown body (summary, risks/rollback, test plan, checklist)

## Policies
- Conventional commits

## Safety
- No push or remote actions without explicit integration

## Triggers & Commands
- `agent commit msg --scope "<scope>" --summary "<summary>"`
- `agent pr create --base <branch> --head <branch>`

## Success Criteria
- Clear commit history and actionable PRs

## Rollback
- Reword commits or amend PR body as needed
