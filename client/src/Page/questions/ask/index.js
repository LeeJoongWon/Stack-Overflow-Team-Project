import { useState } from 'react';
import styled from 'styled-components';
import AskHeader from './AskComponent/AskHeader';
import StyledButton from './AskComponent/Btn';
import ChainBox from './AskComponent/ChainBox';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import useConfirm from '../../components/hook/useConfirm';
import { useCookies } from 'react-cookie';

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    max-width: 1264px;
    flex: 1 0 auto;
    justify-content: space-between;
    margin: 0 auto;

    .buttonSubmit {
        margin-top: -20px;
        margin-bottom: 60px;
    }

    button:disabled {
        border: 0px;
        background-color: #82c7fc;
        cursor: default;
    }
`;

const QuestionAsk = () => {
    const [title, setTitle] = useState('');
    const [problem, setProblem] = useState('');
    const [tag, setTag] = useState('');
    const [submit, setSubmit] = useState(false);
    const navigate = useNavigate();

    const deleteConfirm = () => {
        setTitle('');
        setProblem('');
        setTag('');
    };

    const cancelConfirm = () => {};

    const confirmDelete = useConfirm('정말 삭제하시겠습니까?', deleteConfirm, cancelConfirm);


    const [cookie] = useCookies(['memberId']);

    const handleSubmit = () => {
        const data = {
            title: title,
            content: problem,
            memberId: cookie.memberId,
        };

        if (title.length === 0) {
            alert('제목은 1글자 이상 입력해주세요');
            return;
        }
        if (problem.length <= 5) {
            alert('본문은 20글자 이상 입력해주세요');
            return;
        }

        axios
            .post('http://ec2-52-78-166-35.ap-northeast-2.compute.amazonaws.com:8080/questions', data)
            .then((res) => {
                if (res.status === 201) {
                    alert('질문이 등록되었습니다.');
                    navigate('/questions');
                }
            })
            .catch((e) => console.log(e));
    };

    return (
        <>
            <Wrapper>
                <AskHeader />
                <ChainBox title={title} setTitle={setTitle} problem={problem} setProblem={setProblem} tag={tag} setTag={setTag} setSubmit={setSubmit} />
                <div className="buttonSubmit">
                    <StyledButton type="button" onClick={handleSubmit} disabled={submit ? false : true}>
                        Review your question
                    </StyledButton>
                    <StyledButton onClick={confirmDelete} color="red" background="white">
                        Discard draft
                    </StyledButton>
                </div>
            </Wrapper>
        </>
    );
};

export default QuestionAsk;
